package mygame;



import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

public class FireControl extends AbstractControl {
   
    float timer = .4f;
   
    private SimpleApplication app;
    FireControl() {
        
    }
   
    @Override
    protected void controlUpdate(float tpf) {
        timer = timer - 0.1f * tpf;
        if(timer < 0) {
            spatial.getParent().detachChild(spatial);
        }
    }


public void initialize(AppStateManager stateManager, Application app) {



this.app = (SimpleApplication) app;

}
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}

    
}