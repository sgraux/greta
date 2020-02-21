/*
 * This file is part of the auxiliaries of Greta.
 *
 * Greta is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Greta is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Greta.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
package greta.auxiliary.openface;

import greta.core.util.math.Vec3d;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This file represents an OpenFace2 frame
 * 
 * @author Philippe Gauthier <philippe.gauthier@sorbonne-universite.fr>
 * @author Brice Donval
 */
public class OpenFaceFrame {

    /*private class AUStruct {
        int readIndex;
        int index;
        double value;
        double intensity;
        String label;
        AUStruct(String label, int colIndex, int au) {
            this.label = label;
            this.index = au;
            this.readIndex = colIndex;
        }
    }*/
    private final static int MAX_AUS = 100;
    int frameId = 0;
    int face_id = 0;

    double timeStamp = 0;
    double confidence = 0;

    boolean success = false;

    Vec3d headPos = new Vec3d();
    Vec3d headRot = new Vec3d();

    Vec3d gaze0 = new Vec3d();
    Vec3d gaze1 = new Vec3d();

    double gazeAngleX;
    double gazeAngleY;

    double[] au_r = new double[MAX_AUS];
    double[] au_c = new double[MAX_AUS];
    double[] intensity = new double[MAX_AUS];

    private static int[] au_index = new int[MAX_AUS];
    private static int[] au_list = new int[MAX_AUS];

    static String[] headers;

    public static int auRealSize;
    public static int col_blink;
    public static String separator = ",";

    public static boolean readHeader(String line) {
        String[] headPoseCols = line.split(separator);        
        Pattern p = Pattern.compile("AU([0-9]+)_r");
        int countAus = 0;
        if (headers != null && Arrays.equals(headers, headPoseCols)){
            return false;
        }

        for (int i = 0; i < headPoseCols.length; i++) {
            if ("AU45_r".equals(headPoseCols[i])) {
                col_blink = i;
            }
            Matcher m = p.matcher(headPoseCols[i]);
            if (m.matches()) {
                int auIndex = Integer.parseInt(m.group(1));
                au_index[auIndex] = i;
                au_list[countAus] = auIndex;
                countAus++;
            }
        }

        auRealSize = countAus;
        headers = headPoseCols;        

        return true;
    }

    public static int getAUIndex(int i) {
        return au_index[au_list[i]];    
    }

    public double getBlink() {
        return au_r[col_blink];
    }

    public void readDataLine(String data) {

        String[] cols = data.split(separator);
        int i = 0;

        frameId = Integer.parseInt(cols[i++]);
        face_id = Integer.parseInt(cols[i++]);
        timeStamp = Double.parseDouble(cols[i++]);
        confidence = Double.parseDouble(cols[i++]);
        success = Integer.parseInt(cols[i++])==1;  

        headPos.set(Double.parseDouble(cols[i++]),  Double.parseDouble(cols[i++]),  Double.parseDouble(cols[i++]));
        headRot.set(Double.parseDouble(cols[i++]),  Double.parseDouble(cols[i++]),  Double.parseDouble(cols[i++]));
        gaze0.set(Double.parseDouble(cols[i++]),    Double.parseDouble(cols[i++]),  Double.parseDouble(cols[i++]));
        gaze1.set(Double.parseDouble(cols[i++]),    Double.parseDouble(cols[i++]),  Double.parseDouble(cols[i++]));

        gazeAngleX = Double.parseDouble(cols[i++]);
        gazeAngleY = Double.parseDouble(cols[i++]);

        if (auRealSize == 0) {
            auRealSize = (cols.length-i) / 2;
        }

        int j;
        for (j = 0; j < auRealSize; j++) {
            au_r[j] = Double.parseDouble(cols[i+j]);
        }
        i = i+j;
        for (j = 0; j < auRealSize; j++) {
            au_c[j] = Double.parseDouble(cols[i+j]);
        }
    }
/*
    public boolean isNumeric(String s) {  
        return s.matches("[-+ ]?\\d*\\.?\\d+");  
    }
*/
    public OpenFaceFrame clone(){
        OpenFaceFrame f = new OpenFaceFrame();
        f.copy(this);
        return f;
    }
    void copy(OpenFaceFrame f) {
        frameId 		=	f.frameId;
        face_id 	=	f.face_id;
        timeStamp 	=	f.timeStamp;
        confidence 	=	f.confidence;
        success 	=	f.success;
        headPos 	=	f.headPos.clone();
        headRot 	=	f.headRot.clone();
        gaze0 		=	f.gaze0.clone();
        gaze1 		=	f.gaze1.clone();
        gazeAngleX	=	f.gazeAngleX	;
        gazeAngleY	=	f.gazeAngleY	;
        System.arraycopy( f.au_r, 0, au_r, 0, f.au_r.length );
        System.arraycopy( f.au_c, 0, au_c, 0, f.au_c.length );
        System.arraycopy( f.intensity, 0, intensity, 0, f.intensity.length );
    }
}