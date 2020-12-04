package mygame;



import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class ShootControl extends AbstractControl {
    Vector3f loc;
    private SimpleApplication app;
    ShootControl(Vector3f loc) {
        this.loc = loc;
    }

    @Override
    protected void controlUpdate(float tpf) {
       spatial.move(loc.mult(tpf * 40));
    }


public void initialize(AppStateManager stateManager, Application app) {



this.app = (SimpleApplication) app;

}
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}

    
}