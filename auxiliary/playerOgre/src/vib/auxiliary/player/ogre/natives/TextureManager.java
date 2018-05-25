/*
 *  This file is part of the auxiliaries of VIB (Virtual Interactive Behaviour).
 */
package vib.auxiliary.player.ogre.natives;

/**
 *
 * @author André-Marie
 */
public class TextureManager extends _Object_ {

    public static TextureManager getSingleton() {
        return new TextureManager(_getSingleton());
    }
    private static native long _getSingleton();

    public TextureManager(long pointer) {
        super(pointer);
    }

    public void remove(String string) {
        _remove(getNativePointer(), string);
    }
    private native void _remove(long p, String s);

    public Texture createRenderTexture(String string, int textureWidth, int textureHeight) {
        return new Texture(_createRenderTexture(getNativePointer(), string, textureWidth, textureHeight));
    }
    
    
//        vib.auxiliary.player.ogre.natives.TextureManager.getSingleton().createManual(
//                            texturePtr,
//                            "VIBRenderTexture-"+this.toString(),
//                            vib.auxiliary.player.ogre.natives.ResourceGroupManager.getDEFAULT_RESOURCE_GROUP_NAME(),
//                            vib.auxiliary.player.ogre.natives.TextureType.TEX_TYPE_2D,
//                            textureWidth,
//                            textureHeight,
//                            1,
//                            0,
//                            vib.auxiliary.player.ogre.natives.PixelFormat.PF_BYTE_RGB,
//                            vib.auxiliary.player.ogre.natives.TextureUsage.TU_RENDERTARGET.getValue(),
//                            new vib.auxiliary.player.ogre.natives.ManualResourceLoader(WithoutNativeObject.I_WILL_DELETE_THIS_OBJECT),
//                            false,
//                            4);
    private native long _createRenderTexture(long p, String string, int textureWidth, int textureHeight);
    
    @Override
    protected native void delete(long nativePointer);
}
