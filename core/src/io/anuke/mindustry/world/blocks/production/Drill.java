package io.anuke.mindustry.world.blocks.production;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.content.Liquids;
import io.anuke.mindustry.content.fx.BlockFx;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.graphics.Layer;
import io.anuke.mindustry.type.Item;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.consumers.ConsumeLiquid;
import io.anuke.mindustry.world.meta.BlockGroup;
import io.anuke.mindustry.world.meta.BlockStat;
import io.anuke.mindustry.world.meta.StatUnit;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Effects.Effect;
import io.anuke.ucore.core.Graphics;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Mathf;

import static io.anuke.mindustry.Vars.control;
import static io.anuke.mindustry.Vars.headless;

public class Drill extends Block{
    protected final static float hardnessDrillMultiplier = 50f;
    protected final int timerDump = timers++;

    protected final Array<Tile> drawTiles = new Array<>();
    protected final Array<Item> toAdd = new Array<>();

    /**Maximum tier of blocks this drill can mine.*/
    protected int tier;
    /**Base time to drill one ore, in frames.*/
    protected float drillTime = 300;
    /**Whether the liquid is required to drill. If false, then it will be used as a speed booster.*/
    protected boolean liquidRequired = false;
    /**How many times faster the drill will progress when boosted by liquid.*/
    protected float liquidBoostIntensity = 1.6f;
    /**Speed at which the drill speeds up.*/
    protected float warmupSpeed = 0.02f;

    /**Effect played when an item is produced. This is colored.*/
    protected Effect drillEffect = BlockFx.mine;
    /**Speed the drill bit rotates at.*/
    protected float rotateSpeed = 2f;
    /**Effect randomly played while drilling.*/
    protected Effect updateEffect = BlockFx.pulverizeSmall;
    /**Chance the update effect will appear.*/
    protected float updateEffectChance = 0.02f;

    protected boolean drawRim = false;

    protected Color heatColor = Color.valueOf("ff5512");
    protected TextureRegion rimRegion;
    protected TextureRegion rotatorRegion;
    protected TextureRegion topRegion;

    public Drill(String name){
        super(name);
        update = true;
        solid = true;
        layer = Layer.overlay;
        itemCapacity = 5;
        group = BlockGroup.drills;
        hasLiquids = true;
        liquidCapacity = 5f;
        hasItems = true;

        consumes.add(new ConsumeLiquid(Liquids.water, 0.05f)).optional(true);
    }

    @Override
    public void load(){
        super.load();
        rimRegion = Draw.region(name + "-rim");
        rotatorRegion = Draw.region(name + "-rotator");
        topRegion = Draw.region(name + "-top");
    }

    @Override
    public void draw(Tile tile){
        float s = 0.3f;
        float ts = 0.6f;

        DrillEntity entity = tile.entity();

        Draw.rect(region, tile.drawx(), tile.drawy());

        if(drawRim){
            Graphics.setAdditiveBlending();
            Draw.color(heatColor);
            Draw.alpha(entity.warmup * ts * (1f - s + Mathf.absin(Timers.time(), 3f, s)));
            Draw.rect(rimRegion, tile.drawx(), tile.drawy());
            Draw.color();
            Graphics.setNormalBlending();
        }

        Draw.rect(rotatorRegion, tile.drawx(), tile.drawy(), entity.drillTime * rotateSpeed);

        Draw.rect(topRegion, tile.drawx(), tile.drawy());

        if(!isMultiblock() && isValid(tile)){
            Draw.color(tile.floor().drops.item.color);
            Draw.rect("blank", tile.worldx(), tile.worldy(), 2f, 2f);
            Draw.color();
        }
    }

    @Override
    public TextureRegion[] getIcon(){
        return new TextureRegion[]{Draw.region(name), Draw.region(name + "-rotator"), Draw.region(name + "-top")};
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(BlockStat.drillTier, table -> {
            Array<Item> list = new Array<>();

            for(Item item : Item.all()){
                if(tier >= item.hardness && Draw.hasRegion(item.name + "1")){
                    list.add(item);
                }
            }

            for(int i = 0; i < list.size; i++){
                Item item = list.get(i);
                table.addImage(item.name + "1").size(8 * 3).padRight(2).padLeft(2).padTop(3).padBottom(3);
                if(i != list.size - 1){
                    table.add("/");
                }
            }
        });

        stats.add(BlockStat.drillSpeed, 60f / drillTime, StatUnit.itemsSecond);
    }

    @Override
    public void update(Tile tile){
        toAdd.clear();

        DrillEntity entity = tile.entity();

        float multiplier = 0f;
        float totalHardness = 0f;

        for(Tile other : tile.getLinkedTiles(tempTiles)){
            if(isValid(other)){
                Item drop = getDrop(other);
                toAdd.add(drop);
                totalHardness += drop.hardness;
                multiplier += 1f;
            }
        }

        if(entity.timer.get(timerDump, 15)){
            tryDump(tile);
        }

        entity.drillTime += entity.warmup * Timers.delta();

        if(entity.items.total() < itemCapacity && toAdd.size > 0 && entity.cons.valid()){

            float speed = 1f;

            if(entity.consumed(ConsumeLiquid.class) && !liquidRequired){
                speed = liquidBoostIntensity;
            }

            entity.warmup = Mathf.lerpDelta(entity.warmup, speed, warmupSpeed);
            entity.progress += Timers.delta() * multiplier * speed * entity.warmup;

            if(Mathf.chance(Timers.delta() * updateEffectChance * entity.warmup))
                Effects.effect(updateEffect, entity.x + Mathf.range(size * 2f), entity.y + Mathf.range(size * 2f));
        }else{
            entity.warmup = Mathf.lerpDelta(entity.warmup, 0f, warmupSpeed);
            return;
        }

        if(toAdd.size > 0 && entity.progress >= drillTime + hardnessDrillMultiplier * Math.max(totalHardness, 1f) / multiplier
                && tile.entity.items.total() < itemCapacity){

            int index = entity.index % toAdd.size;
            offloadNear(tile, toAdd.get(index));

            //unlock item content
            if(!headless){
                control.database().unlockContent(toAdd.get(index));
            }

            entity.index++;
            entity.progress = 0f;

            Effects.effect(drillEffect, toAdd.get(index).color,
                    entity.x + Mathf.range(size), entity.y + Mathf.range(size));
        }
    }

    @Override
    public boolean canPlaceOn(Tile tile){
        if(isMultiblock()){
            for(Tile other : tile.getLinkedTilesAs(this, drawTiles)){
                if(isValid(other)){
                    return true;
                }
            }
            return false;
        }else{
            return isValid(tile);
        }
    }

    @Override
    public TileEntity getEntity(){
        return new DrillEntity();
    }

    public Item getDrop(Tile tile){
        return tile.floor().drops.item;
    }

    protected boolean isValid(Tile tile){
        return tile.floor().drops != null && tile.floor().drops.item.hardness <= tier;
    }

    public static class DrillEntity extends TileEntity{
        public float progress;
        public int index;
        public float warmup;
        public float drillTime;
    }

}
