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

public class FollowControl extends AbstractControl {
    Vector3f playerLocation;
    Vector3f npcLocation;
    float jumpCooldown;
    private Vector3f walkDirection1 = new Vector3f();
    boolean healthDown;
    private SimpleApplication app;
    FollowControl(Vector3f playerLocation) {
        this.playerLocation = playerLocation;
    }
    public void setLoc(Vector3f lec) {
        playerLocation = lec;
    }
    @Override
    protected void controlUpdate(float tpf) {
        CharacterControl car = spatial.getControl(CharacterControl.class);
         walkDirection1.set(0,0,0);
         npcLocation = car.getPhysicsLocation();
         System.out.println(playerLocation);
         float x = playerLocation.x - npcLocation.x; //we get the relative value
        float z = playerLocation.z - npcLocation.z; //we get the relative value
        //at this part i was lazy, it should be the hypotenuse but i didn't done the whole math haha...
        float maxMove = FastMath.abs(x) + FastMath.abs(z);
        float heightDif = playerLocation.y - npcLocation.y;
       
        if(maxMove > 3){
            walkDirection1.addLocal(x/maxMove, 0, z/maxMove);
            healthDown = false;
        } else {
            
            healthDown = true;
        }
        spatial.lookAt(new Vector3f(playerLocation.x,npcLocation.y,playerLocation.z), new Vector3f(0,1,0));
        spatial.getControl(CharacterControl.class).setWalkDirection(walkDirection1.multLocal(0.3f));
        
       
        
    }


public void initialize(AppStateManager stateManager, Application app) {



this.app = (SimpleApplication) app;

}
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}

    
}