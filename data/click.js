importPackage(com.celoron.engine); 
importPackage(com.celoron.test); 
importPackage(java.util); 
importPackage(com.badlogic.gdx.graphics); 
importPackage(com.badlogic.gdx); 
importPackage(com.badlogic.gdx.math); 
importPackage(com.badlogic.gdx.physics.box2d); 

generator = new Random();
t=game.asset.getTexture("data/box.jpg");
scale= (generator.nextDouble()+1)/10;

entity = new Entity("falling box", game);

entity.setScale(scale);
entity.getPosition().x = -Gdx.graphics.getWidth()/4+generator.nextInt(Gdx.graphics.getWidth()/2);
entity.getPosition().y = Gdx.graphics.getHeight()/4;

entity.AddComponent(new TextureRender("render", t));
entity.AddComponent(new PhysicComp("phy", new Vector2(256, 256).mul(scale), BodyDef.BodyType.DynamicBody));
game.scene.addEntity(entity);