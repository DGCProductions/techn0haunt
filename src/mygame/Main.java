package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.ScreenshotAppState;
import com.jme3.app.state.VideoRecorderAppState;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import java.util.Random;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.LightControl;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author DGC
 */
public class Main extends SimpleApplication implements ActionListener,PhysicsCollisionListener {
    BulletAppState bulletAppState;
    Picture crosshair;
    Picture hp_dis;
    Picture over;
      Picture top;
      boolean shooting = false;
      Picture amm_pic;
      float damageTimer = 0;
      Node shootables;
      Node model;
      Picture store;
      AudioNode music;
      AudioNode shoot;
      AudioNode endmu;
     
      BitmapText ammo_txt;
       BitmapText hp_txt;
       BitmapText points_txt;
       BitmapText waves;
       int ended = 0;
       Vector3f playerLocation;;
        BitmapText timer;
      int ammo = 120;
      int enemie = 10;
      int hp = 100;
      int wave = 1;
      int points = 50;
      int lastEnemies;
      float prep = 19;
      Picture blood;
      int stage = 1;
      float spawntime = 1;
       Spatial a;
       Node bullets;
       Node enemies;
       float time;
     RigidBodyControl landscape;
      private Vector3f walkDirection = new Vector3f();
      private CharacterControl player;
             private Vector3f walkDirection1 = new Vector3f();
            private boolean left = false, right = false, up = false,
                    down = false;
            boolean zoom = false;
             private Vector3f camDir = new Vector3f();
  private Vector3f camLeft = new Vector3f();
  
    public static void main(String[] args) {
         AppSettings settings = new AppSettings(true);
 
          settings.setTitle("Techn0Haunt");
         settings.setSettingsDialogImage("Textures/techn0haunt.png");
        Main app = new Main();
        app.setSettings(settings);
        
        app.start();
    }

    @Override
    public void simpleInitApp() {
           ScreenshotAppState screenShotState = new ScreenshotAppState();
this.stateManager.attach(screenShotState);
      
        shootables = new Node("shootables");
        rootNode.attachChild(shootables);
        bullets = new Node("bullets");
        rootNode.attachChild(bullets);
        enemies = new Node("enemies");
        rootNode.attachChild(enemies);
        enemies.rotate(0, -1, 0);
        
         bulletAppState = new BulletAppState();
    stateManager.attach(bulletAppState);
        a =  assetManager.loadModel("Models/techn0haunt_map.j3o");
        shootables = new Node("shootables");
        rootNode.attachChild(shootables);
        shootables.attachChild(a);
        
         model = (Node) assetManager.loadModel("Models/techn0gun1.j3o");
        rootNode.attachChild(model);
        a.scale(3);
        CollisionShape sceneShape =
            CollisionShapeFactory.createMeshShape(a);
    landscape = new RigidBodyControl(sceneShape, 0);
    a.addControl(landscape);
    
     CapsuleCollisionShape capsuleShape1 = new CapsuleCollisionShape(1.5f, .7f, 1);
    player = new CharacterControl(capsuleShape1, 0.05f);
    
    
    player.setJumpSpeed(20);
    player.setFallSpeed(30);
  setUpKeys();
   
    player.setGravity(new Vector3f(0,-30f,0));
    player.setPhysicsLocation(new Vector3f(0, 10, 0));
    flyCam.setMoveSpeed(50);
    flyCam.setZoomSpeed(0);

rootNode.attachChild(a);
bulletAppState.getPhysicsSpace().add(landscape);

bulletAppState.getPhysicsSpace().add(player);

crosshair= new Picture("start");
         crosshair.setImage(assetManager, "Textures/crosshair-transparent-5.png", true);
         crosshair.setWidth(settings.getWidth() / 19.2f);
crosshair.setHeight(settings.getWidth() / 19.2f);
crosshair.setLocalTranslation(settings.getWidth() / 2f - (settings.getWidth() / 19.2f / 2), settings.getHeight() / 2f - (settings.getWidth() / 19.2f / 2), 0);

guiNode.attachChild(crosshair);
setDisplayFps(false);
setDisplayStatView(false);
hp_dis= new Picture("start");
         hp_dis.setImage(assetManager, "Textures/hp_dis.png", true);
         hp_dis.setWidth(settings.getWidth()/4.8f);
hp_dis.setHeight(settings.getWidth()/14.4f);
hp_dis.setLocalTranslation(0,0,0);

guiNode.attachChild(hp_dis);

top= new Picture("start");
         top.setImage(assetManager, "Textures/dadad.png", true);
         top.setWidth(settings.getWidth() / 2);
top.setHeight(settings.getHeight() / 28.8f);
top.setLocalTranslation((settings.getWidth() / 2) / 2,settings.getHeight() - (settings.getHeight() / 28.8f),0);

guiNode.attachChild(top);



blood= new Picture("start");
         blood.setImage(assetManager, "Textures/blood.png", true);
         blood.setWidth(settings.getWidth());
blood.setHeight(settings.getHeight());
blood.setLocalTranslation(0,0,0);



amm_pic= new Picture("start");
         amm_pic.setImage(assetManager, "Textures/dadasd.png", true);
         amm_pic.setWidth(settings.getWidth()/4.8f);
amm_pic.setHeight(settings.getWidth()/14.4f);
amm_pic.setLocalTranslation(settings.getWidth() - (settings.getWidth()/4.8f),0,0);

guiNode.attachChild(amm_pic);
  FilterPostProcessor fpp=new FilterPostProcessor(assetManager);
  BloomFilter bf=new BloomFilter(BloomFilter.GlowMode.SceneAndObjects);
  bf.setDownSamplingFactor(2);
  bf.setBloomIntensity(2);
  bf.setBlurScale(1);
  fpp.addFilter(bf);
  viewPort.addProcessor(fpp);
  
  guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        ammo_txt = new BitmapText(guiFont, false);
        ammo_txt.setText(Integer.toString(ammo));
        ammo_txt.setSize(settings.getWidth() / 28.8f);
        ammo_txt.setColor(ColorRGBA.Green);
        ammo_txt.setLocalTranslation(settings.getWidth() - (settings.getWidth()/10),settings.getWidth() / 28.8f + 5 ,0);
        guiNode.attachChild(ammo_txt);
        
        
        
         hp_txt = new BitmapText(guiFont, false);
        hp_txt.setText(Integer.toString(hp));
        hp_txt.setSize(settings.getWidth() / 28.8f);
        hp_txt.setColor(ColorRGBA.Green);
        hp_txt.setLocalTranslation(settings.getWidth() / 28.8f,settings.getWidth() / 28.8f + 5 ,0);
        guiNode.attachChild(hp_txt);
        
         waves = new BitmapText(guiFont, false);
        waves.setText("Wave: "+Integer.toString(wave));
        waves.setSize(settings.getWidth() / 50.8f);
        waves.setColor(ColorRGBA.Green);
        waves.setLocalTranslation((settings.getWidth() / 2) / 2 + waves.getLineWidth(),settings.getHeight(),0);
        guiNode.attachChild(waves);
        
         points_txt = new BitmapText(guiFont, false);
        points_txt.setText("Points: "+Integer.toString(points));
        points_txt.setSize(settings.getWidth() / 50.8f);
        points_txt.setColor(ColorRGBA.Green);
        points_txt.setLocalTranslation((settings.getWidth() / 2) ,settings.getHeight(),0);
        guiNode.attachChild(points_txt);
        
        store= new Picture("start");
         store.setImage(assetManager, "Textures/store1.png", true);
         store.setWidth(settings.getWidth()/4.8f);
store.setHeight(settings.getWidth()/5.4f);
store.setLocalTranslation(settings.getWidth() - (settings.getWidth()/4.8f),settings.getHeight()/2,0);
        guiNode.attachChild(store);
        
        
        over= new Picture("start");
         over.setImage(assetManager, "Textures/enddeath.png", true);
         over.setWidth(settings.getWidth());
over.setHeight(settings.getHeight());
over.setLocalTranslation(0,0,0);
        initAudio();
        
        timer= new BitmapText(guiFont, false);
        timer.setSize(settings.getWidth() / 40.8f);
        timer.setColor(ColorRGBA.Green);
        timer.setLocalTranslation(settings.getWidth() / 6 - timer.getLineWidth()/2,settings.getHeight()/1.3f,0);
        guiNode.attachChild(timer);
        bulletAppState.setDebugEnabled(false);
        bulletAppState.getPhysicsSpace().addCollisionListener(this);

    }
    
    protected void zoomCamera(boolean a){

if(a) {

float h = cam.getFrustumTop();

float w = cam.getFrustumRight();

float aspect = w / h;



float near = cam.getFrustumNear();



float fovY = FastMath.atan(h / near)

/ (FastMath.DEG_TO_RAD * .5f);

fovY = 20  ;



h = FastMath.tan( fovY * FastMath.DEG_TO_RAD * .5f) * near;

w = h * aspect;



cam.setFrustumTop(h);

cam.setFrustumBottom(-h);

cam.setFrustumLeft(-w);

cam.setFrustumRight(w);
} else {
    float h = cam.getFrustumTop();

float w = cam.getFrustumRight();

float aspect = w / h;



float near = cam.getFrustumNear();



float fovY = FastMath.atan(h / near)

/ (FastMath.DEG_TO_RAD * .5f);

fovY = 45;



h = FastMath.tan( fovY * FastMath.DEG_TO_RAD * .5f) * near;

w = h * aspect;



cam.setFrustumTop(h);

cam.setFrustumBottom(-h);
cam.setFrustumLeft(-w);
cam.setFrustumRight(w);
}
}
    public void makeExplosion(Vector3f loc) {
        
        
       ParticleEmitter fire =
            new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
    Material mat_red = new Material(assetManager,
            "Common/MatDefs/Misc/Particle.j3md");
    mat_red.setTexture("Texture", assetManager.loadTexture(
            "Effects/Explosion/flame.png"));
    fire.setLocalTranslation(loc);
    fire.setMaterial(mat_red);
    fire.setImagesX(2);
    fire.setImagesY(2); // 2x2 texture animation
    fire.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
    fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
    fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
    fire.setStartSize(1.5f);
    fire.setEndSize(0.4f);
    fire.setGravity(0, 1, 0);
    fire.setLowLife(1f);
    fire.setHighLife(3f);
    fire.setRandomAngle(true);
    fire.getParticleInfluencer().setVelocityVariation(0.3f);
    fire.addControl(new FireControl());
    rootNode.attachChild(fire);
    
     
    
    
    }
public void onAction(String binding, boolean isPressed, float tpf) {
    if (binding.equals("Left")) {
      left = isPressed;
    } else if (binding.equals("Right")) {
      right= isPressed;
    } else if (binding.equals("Up")) {
      up = isPressed;
    } else if (binding.equals("Down")) {
      down = isPressed;
    } else if (binding.equals("Jump")) {
      if (isPressed && player.onGround()) { player.jump(new Vector3f(0,10f,0));}
    } else if(binding.equals("Zoom")) {
       zoom = isPressed;
    } else if(binding.equals("ammo") && points >= 10 && !isPressed) {
        if(stage==1) {
        ammo = ammo + 10;
        points = points - 10;
        }
    }
  }

public void setUpKeys() {
    inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
    inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
    inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
     inputManager.addMapping("Zoom", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
      inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    inputManager.addListener(actionListener, "Shoot");
     inputManager.addMapping("ammo", new KeyTrigger(KeyInput.KEY_U));
     inputManager.addListener(this, "ammo");
    inputManager.addListener(this, "Zoom");
    inputManager.addListener(this, "Left");
    inputManager.addListener(this, "Right");
    inputManager.addListener(this, "Up");
    inputManager.addListener(this, "Down");
    inputManager.addListener(this, "Jump");
  }


    @Override
    public void simpleUpdate(float tpf) {
         playerLocation = player.getPhysicsLocation();
         ammo_txt.setText(Integer.toString(ammo));
         hp_txt.setText(Integer.toString(hp));
         points_txt.setText("Points: "+Integer.toString(points));
         waves.setText("Wave: "+Integer.toString(wave));
         if(stage == 1) {
             prep = prep - 1 * tpf;
             int prep2 = Math.round(prep);
             timer.setText(Float.toString(prep2));
             if(prep < 0) {
                 stage = 2;
                 lastEnemies = enemie;
                 guiNode.detachChild(store);
                 timer.setText("T.F.R left:" + Integer.toString(enemie));
             }
         }
         if(stage == 2) {
              timer.setText("T.F.R left:" + Integer.toString(enemie));
              if(enemie <= 0) {
                  guiNode.attachChild(store);
                  stage = 1;
                  wave = wave + 1;
                  enemie = lastEnemies + 5;
                  prep = 10;
              }
         }
         if(stage == 2 && spawntime < 0 && enemies.getQuantity() < enemie) {
             Random random = new Random();
             int a =random.nextInt((3 - 0) + 1) + 0;
              Node monster = (Node) assetManager.loadModel("Models/techn0robot.j3o");
              monster.setLocalScale(.5f);
              monster.rotate(0, 0, 0);
              if(a == 0) {
                  monster.setLocalTranslation(-48.432163f, 11, -47.145016f);
              } else if(a==1) {
                  monster.setLocalTranslation(48.432163f, 11, -47.145016f);
              } else if(a==2) {
                  monster.setLocalTranslation(-48.432163f, 11, 47.145016f);
              } else if(a==3) {
                  monster.setLocalTranslation(48.432163f, 11, 47.145016f);
              } 
             
              CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 1f, 1);
              CharacterControl monster_phys = new CharacterControl(capsuleShape, 0.05f);
              
              monster_phys.setJumpSpeed(20f);
              
              
              monster_phys.setFallSpeed(30);
               if(a == 0) {
                   monster_phys.setPhysicsLocation(new Vector3f(-48.432163f, 11, -47.145016f));
              } else if(a==1) {
                   monster_phys.setPhysicsLocation(new Vector3f(48.432163f, 11, -47.145016f));
              } else if(a==2) {
                  monster_phys.setPhysicsLocation(new Vector3f(-48.432163f, 11, 47.145016f));
              } else if(a==3) {
                  monster_phys.setPhysicsLocation(new Vector3f(48.432163f, 11, 47.145016f));
              } 
             
              monster_phys.setGravity(new Vector3f(0,-30f,0));
              monster.addControl(monster_phys);
              monster.addControl(new FollowControl(player.getPhysicsLocation()));
              monster_phys.setUseViewDirection(false);
              bulletAppState.getPhysicsSpace().add(monster_phys);
              enemies.attachChild(monster);
              spawntime = 1;
         }
         spawntime = spawntime - 1 * tpf;
         if(cam.getUp().y < 0)
{
cam.lookAtDirection( new Vector3f(0,cam.getDirection().y,0),new Vector3f(cam.getUp().x,0, cam.getUp().z));
}
          camDir = new Vector3f(cam.getDirection().getX(),0,cam.getDirection().getZ()).normalizeLocal().multLocal(.2f);
        camLeft = cam.getLeft().clone().multLocal(0.2f);
        camDir.y = 0;
        camLeft.y = 0;
        walkDirection.set(0, 0, 0);
        if(cam.getUp().y < .01) {
           
        } else {
        
        if (left) {
            walkDirection.addLocal(camLeft);
        }
        if (right) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (up) {
            walkDirection.addLocal(camDir);
        }
        if (down) {
            walkDirection.addLocal(camDir.negate());
        }
        }
        player.setWalkDirection(walkDirection);
        
        cam.setLocation(player.getPhysicsLocation());
        
        if(zoom) {
            zoomCamera(true);
        } else {
             zoomCamera(false);
        }
        
         Vector3f vectorDifference = new Vector3f(cam.getLocation().subtract(model.getWorldTranslation()));
        model.setLocalTranslation(vectorDifference.addLocal(model.getLocalTranslation()));
        
        Quaternion worldDiff = new Quaternion(cam.getRotation().mult(model.getWorldRotation().inverse()));
        model.setLocalRotation(worldDiff.multLocal(model.getLocalRotation()));
        //Move gun to the bottom right of the screen
        model.move(cam.getDirection().mult(2f));
        model.move(cam.getUp().mult(-.4f));
        model.move(cam.getLeft().mult(-.5f));
        model.rotate(0.0f,1.6f,0);
        
        if(damageTimer >0) {
            guiNode.attachChild(blood);
        } else {
            guiNode.detachChild(blood);
        }
        
        damageTimer = damageTimer - 1 * tpf;
        
        if(shooting && time < 0 && ammo > 0 && ended ==0) {
            
            shoot.playInstance();
          Spatial shoot = assetManager.loadModel("Models/shoot.j3o");
         
          shoot.lookAt(cam.getDirection(), new Vector3f(0,1,0));
          shoot.scale(.25f);
          shoot.setLocalTranslation(model.getLocalTranslation());
            BoxCollisionShape bull = new BoxCollisionShape(shoot.getLocalScale());
            GhostControl bul = new GhostControl(bull);
            bul.setPhysicsRotation(cam.getRotation());
            bul.setPhysicsLocation(shoot.getLocalTranslation());
          shoot.addControl(bul);
           bullets.attachChild(shoot);
           
         bulletAppState.getPhysicsSpace().add(bul);
          
        
          shoot.addControl(new ShootControl(cam.getDirection()));
           
            
           
            
          time = .1f;
          ammo = ammo - 1;
          ammo_txt.setText(Integer.toString(ammo));
        } else {
        time = time - 1 * tpf;
    }
    for(int a = 0; a < enemies.getQuantity(); a++) {
        try {
             enemies.getChild(a).getControl(CharacterControl.class).setEnabled(true);
           enemies.getChild(a).getControl(FollowControl.class).setLoc(player.getPhysicsLocation());
           if(enemies.getChild(a).getControl(FollowControl.class).healthDown) {
               hp = hp - 1;
               damageTimer = 1;
           }
        } catch(Exception e) {
            
        }
    }
    if(hp <= 0 && ended == 0) {
        ended = 1;
       
        
    }
    if(ended == 1) {
        bullets.detachAllChildren();
        enemies.detachAllChildren();
        shootables.detachAllChildren();
        music.stop();
        rootNode.detachChild(model);
        guiNode.detachAllChildren();
        guiNode.attachChild(over);
        endmu.playInstance();
        ended = 2;
        
    }
    if(ended == 2) {
         bullets.detachAllChildren();
        enemies.detachAllChildren();
        shootables.detachAllChildren();
        music.stop();
        rootNode.detachChild(model);
        
         guiNode.attachChild(over);
        guiNode.detachChild(blood);
    }
    }
    @Override
    public void collision(PhysicsCollisionEvent pce) {
        
        for(int i = 0; i < bullets.getQuantity(); i++) {
          
            
        PhysicsCollisionObject a = pce.getObjectA();
        PhysicsCollisionObject b = pce.getObjectB();

        if (a == bullets.getChild(i).getControl(GhostControl.class) && b == landscape || a == landscape && b == bullets.getChild(i).getControl(GhostControl.class)) {
             bullets.getChild(i).getControl(GhostControl.class).setEnabled(false);
            bullets.detachChildAt(i);
        }
        try {
        for(int c = 0; c < enemies.getQuantity(); c++) {
            
          if (a == bullets.getChild(i).getControl(GhostControl.class) && b == enemies.getChild(c).getControl(CharacterControl.class) || a == enemies.getChild(c).getControl(CharacterControl.class) && b == bullets.getChild(i).getControl(GhostControl.class)) {
               makeExplosion(enemies.getChild(c).getControl(CharacterControl.class).getPhysicsLocation());
               
             bullets.getChild(i).getControl(GhostControl.class).setEnabled(false);
              enemies.getChild(c).getControl(CharacterControl.class).setEnabled(false);
             enemies.detachChildAt(c);
             bullets.detachChildAt(i);
             enemie = enemie - 1;
             points = points + 1;
        }
        }
        }catch(Exception e) {
            
        }
        }
        
           }
   
    
    @Override
    public void simpleRender(RenderManager rm) {
    }
    
     private ActionListener actionListener = new ActionListener() {

    public void onAction(String name, boolean keyPressed, float tpf) {
      if (name.equals("Shoot") ) {
          shooting= keyPressed;
      }
      
      
    }
  };
private void initAudio() {
    shoot = new AudioNode(assetManager, "Sounds/pew.wav", AudioData.DataType.Buffer);
    shoot.setPositional(false);
    shoot.setLooping(false);
    shoot.setVolume(4);
    rootNode.attachChild(shoot);
    endmu = new AudioNode(assetManager, "Sounds/death.wav", AudioData.DataType.Buffer);
    endmu.setPositional(false);
    endmu.setLooping(false);
    endmu.setVolume(2);
    rootNode.attachChild(endmu);
    music = new AudioNode(assetManager, "Sounds/music.wav", AudioData.DataType.Stream);
    music.setLooping(true);  // activate continuous playing
    music.setPositional(false);
    music.setVolume(2);
    rootNode.attachChild(music);
    music.play(); // play continuously!
  } 
}
