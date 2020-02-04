package mindustry.gen;

import arc.graphics.Color;
import java.lang.String;
import java.nio.ByteBuffer;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Rules;
import mindustry.game.Team;
import mindustry.net.NetConnection;
import mindustry.net.Packets;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.Tile;

/**
 * Autogenerated file. Do not modify!
 */
public class Call {
  private static final ByteBuffer TEMP_BUFFER = ByteBuffer.allocate(4096);

  public static synchronized void beginBreak(Team team, int x, int y) {
    if(mindustry.Vars.net.server() || !mindustry.Vars.net.active()) {
      mindustry.world.Build.beginBreak(team, x, y);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)0;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeTeam(TEMP_BUFFER, team);
      TEMP_BUFFER.putInt(x);
      TEMP_BUFFER.putInt(y);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void beginPlace(Team team, int x, int y, Block result, int rotation) {
    if(mindustry.Vars.net.server() || !mindustry.Vars.net.active()) {
      mindustry.world.Build.beginPlace(team, x, y, result, rotation);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)1;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeTeam(TEMP_BUFFER, team);
      TEMP_BUFFER.putInt(x);
      TEMP_BUFFER.putInt(y);
      mindustry.io.TypeIO.writeBlock(TEMP_BUFFER, result);
      TEMP_BUFFER.putInt(rotation);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void createBullet(BulletType type, Team team, float x, float y,
      float angle, float velocityScl, float lifetimeScl) {
    if(mindustry.Vars.net.server() || !mindustry.Vars.net.active()) {
      mindustry.entities.bullet.BulletType.createBullet(type, team, x, y, angle, velocityScl, lifetimeScl);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)3;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeBulletType(TEMP_BUFFER, type);
      mindustry.io.TypeIO.writeTeam(TEMP_BUFFER, team);
      TEMP_BUFFER.putFloat(x);
      TEMP_BUFFER.putFloat(y);
      TEMP_BUFFER.putFloat(angle);
      TEMP_BUFFER.putFloat(velocityScl);
      TEMP_BUFFER.putFloat(lifetimeScl);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.udp);
    }
  }

  public static synchronized void createLighting(int seed, Team team, Color color, float damage,
      float x, float y, float rotation, int length) {
    if(mindustry.Vars.net.server() || !mindustry.Vars.net.active()) {
      mindustry.entities.effect.Lightning.createLighting(seed, team, color, damage, x, y, rotation, length);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)4;
      TEMP_BUFFER.position(0);
      TEMP_BUFFER.putInt(seed);
      mindustry.io.TypeIO.writeTeam(TEMP_BUFFER, team);
      mindustry.io.TypeIO.writeColor(TEMP_BUFFER, color);
      TEMP_BUFFER.putFloat(damage);
      TEMP_BUFFER.putFloat(x);
      TEMP_BUFFER.putFloat(y);
      TEMP_BUFFER.putFloat(rotation);
      TEMP_BUFFER.putInt(length);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.udp);
    }
  }

  public static synchronized void launchZone() {
    mindustry.core.Logic.launchZone();
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)6;
      TEMP_BUFFER.position(0);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onBlockSnapshot(short amount, short dataLen, byte[] data) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)2;
      packet.type = (byte)8;
      TEMP_BUFFER.position(0);
      TEMP_BUFFER.putShort(amount);
      TEMP_BUFFER.putShort(dataLen);
      mindustry.io.TypeIO.writeBytes(TEMP_BUFFER, data);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.udp);
    }
  }

  public static synchronized void onBlockSnapshot(NetConnection playerConnection, short amount,
      short dataLen, byte[] data) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)2;
      packet.type = (byte)8;
      TEMP_BUFFER.position(0);
      TEMP_BUFFER.putShort(amount);
      TEMP_BUFFER.putShort(dataLen);
      mindustry.io.TypeIO.writeBytes(TEMP_BUFFER, data);
      packet.writeLength = TEMP_BUFFER.position();
      playerConnection.send(packet, mindustry.net.Net.SendMode.udp);
    }
  }

  public static synchronized void onConnect(NetConnection playerConnection, String ip, int port) {
    if(mindustry.Vars.net.client() || !mindustry.Vars.net.active()) {
      mindustry.core.NetClient.onConnect(ip, port);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)10;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeString(TEMP_BUFFER, ip);
      TEMP_BUFFER.putInt(port);
      packet.writeLength = TEMP_BUFFER.position();
      playerConnection.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onConstructFinish(Tile tile, Block block, int builderID,
      byte rotation, Team team, boolean skipConfig) {
    if(mindustry.Vars.net.server() || !mindustry.Vars.net.active()) {
      mindustry.world.blocks.BuildBlock.onConstructFinish(tile, block, builderID, rotation, team, skipConfig);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)11;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeTile(TEMP_BUFFER, tile);
      mindustry.io.TypeIO.writeBlock(TEMP_BUFFER, block);
      TEMP_BUFFER.putInt(builderID);
      TEMP_BUFFER.put(rotation);
      mindustry.io.TypeIO.writeTeam(TEMP_BUFFER, team);
      TEMP_BUFFER.put(skipConfig ? (byte)1 : 0);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onDeconstructFinish(Tile tile, Block block, int builderID) {
    if(mindustry.Vars.net.server() || !mindustry.Vars.net.active()) {
      mindustry.world.blocks.BuildBlock.onDeconstructFinish(tile, block, builderID);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)12;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeTile(TEMP_BUFFER, tile);
      mindustry.io.TypeIO.writeBlock(TEMP_BUFFER, block);
      TEMP_BUFFER.putInt(builderID);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onEntitySnapshot(NetConnection playerConnection, short amount,
      short dataLen, byte[] data) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)2;
      packet.type = (byte)14;
      TEMP_BUFFER.position(0);
      TEMP_BUFFER.putShort(amount);
      TEMP_BUFFER.putShort(dataLen);
      mindustry.io.TypeIO.writeBytes(TEMP_BUFFER, data);
      packet.writeLength = TEMP_BUFFER.position();
      playerConnection.send(packet, mindustry.net.Net.SendMode.udp);
    }
  }

  public static synchronized void onGameOver(Team winner) {
    mindustry.core.Logic.onGameOver(winner);
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)15;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeTeam(TEMP_BUFFER, winner);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onInfoMessage(String message) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)16;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeString(TEMP_BUFFER, message);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onInfoMessage(NetConnection playerConnection, String message) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)16;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeString(TEMP_BUFFER, message);
      packet.writeLength = TEMP_BUFFER.position();
      playerConnection.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onInfoPopup(String message, float duration, int align, int top,
      int left, int bottom, int right) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)17;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeString(TEMP_BUFFER, message);
      TEMP_BUFFER.putFloat(duration);
      TEMP_BUFFER.putInt(align);
      TEMP_BUFFER.putInt(top);
      TEMP_BUFFER.putInt(left);
      TEMP_BUFFER.putInt(bottom);
      TEMP_BUFFER.putInt(right);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onInfoPopup(NetConnection playerConnection, String message,
      float duration, int align, int top, int left, int bottom, int right) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)17;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeString(TEMP_BUFFER, message);
      TEMP_BUFFER.putFloat(duration);
      TEMP_BUFFER.putInt(align);
      TEMP_BUFFER.putInt(top);
      TEMP_BUFFER.putInt(left);
      TEMP_BUFFER.putInt(bottom);
      TEMP_BUFFER.putInt(right);
      packet.writeLength = TEMP_BUFFER.position();
      playerConnection.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onInfoToast(String message, float duration) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)18;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeString(TEMP_BUFFER, message);
      TEMP_BUFFER.putFloat(duration);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onInfoToast(NetConnection playerConnection, String message,
      float duration) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)18;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeString(TEMP_BUFFER, message);
      TEMP_BUFFER.putFloat(duration);
      packet.writeLength = TEMP_BUFFER.position();
      playerConnection.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onKick(NetConnection playerConnection, String reason) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)1;
      packet.type = (byte)19;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeString(TEMP_BUFFER, reason);
      packet.writeLength = TEMP_BUFFER.position();
      playerConnection.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onKick(NetConnection playerConnection,
      Packets.KickReason reason) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)1;
      packet.type = (byte)20;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeKick(TEMP_BUFFER, reason);
      packet.writeLength = TEMP_BUFFER.position();
      playerConnection.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onLabel(String info, float duration, float worldx, float worldy) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)21;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeString(TEMP_BUFFER, info);
      TEMP_BUFFER.putFloat(duration);
      TEMP_BUFFER.putFloat(worldx);
      TEMP_BUFFER.putFloat(worldy);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onLabel(NetConnection playerConnection, String info,
      float duration, float worldx, float worldy) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)21;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeString(TEMP_BUFFER, info);
      TEMP_BUFFER.putFloat(duration);
      TEMP_BUFFER.putFloat(worldx);
      TEMP_BUFFER.putFloat(worldy);
      packet.writeLength = TEMP_BUFFER.position();
      playerConnection.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onMechFactoryDone(Tile tile) {
    if(mindustry.Vars.net.server() || !mindustry.Vars.net.active()) {
      mindustry.world.blocks.units.MechPad.onMechFactoryDone(tile);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)22;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeTile(TEMP_BUFFER, tile);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onPingResponse(NetConnection playerConnection, long time) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)25;
      TEMP_BUFFER.position(0);
      TEMP_BUFFER.putLong(time);
      packet.writeLength = TEMP_BUFFER.position();
      playerConnection.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onPlayerDisconnect(int playerid) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)26;
      TEMP_BUFFER.position(0);
      TEMP_BUFFER.putInt(playerid);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onPositionSet(NetConnection playerConnection, float x, float y) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)27;
      TEMP_BUFFER.position(0);
      TEMP_BUFFER.putFloat(x);
      TEMP_BUFFER.putFloat(y);
      packet.writeLength = TEMP_BUFFER.position();
      playerConnection.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onPuddleRemoved(int puddleid) {
    if(mindustry.Vars.net.server() || !mindustry.Vars.net.active()) {
      mindustry.entities.effect.Puddle.onPuddleRemoved(puddleid);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)28;
      TEMP_BUFFER.position(0);
      TEMP_BUFFER.putInt(puddleid);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onRemoveFire(int fid) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)29;
      TEMP_BUFFER.position(0);
      TEMP_BUFFER.putInt(fid);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onSetRules(Rules rules) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)30;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeRules(TEMP_BUFFER, rules);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onSetRules(NetConnection playerConnection, Rules rules) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)30;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeRules(TEMP_BUFFER, rules);
      packet.writeLength = TEMP_BUFFER.position();
      playerConnection.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onStateSnapshot(NetConnection playerConnection, float waveTime,
      int wave, int enemies, short coreDataLen, byte[] coreData) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)2;
      packet.type = (byte)31;
      TEMP_BUFFER.position(0);
      TEMP_BUFFER.putFloat(waveTime);
      TEMP_BUFFER.putInt(wave);
      TEMP_BUFFER.putInt(enemies);
      TEMP_BUFFER.putShort(coreDataLen);
      mindustry.io.TypeIO.writeBytes(TEMP_BUFFER, coreData);
      packet.writeLength = TEMP_BUFFER.position();
      playerConnection.send(packet, mindustry.net.Net.SendMode.udp);
    }
  }

  public static synchronized void onTileDamage(Tile tile, float health) {
    if(mindustry.Vars.net.server() || !mindustry.Vars.net.active()) {
      mindustry.world.Tile.onTileDamage(tile, health);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)33;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeTile(TEMP_BUFFER, tile);
      TEMP_BUFFER.putFloat(health);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.udp);
    }
  }

  public static synchronized void onTileDestroyed(Tile tile) {
    if(mindustry.Vars.net.server() || !mindustry.Vars.net.active()) {
      mindustry.world.Tile.onTileDestroyed(tile);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)34;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeTile(TEMP_BUFFER, tile);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onUnitFactorySpawn(Tile tile, int spawns) {
    if(mindustry.Vars.net.server() || !mindustry.Vars.net.active()) {
      mindustry.world.blocks.units.UnitFactory.onUnitFactorySpawn(tile, spawns);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)37;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeTile(TEMP_BUFFER, tile);
      TEMP_BUFFER.putInt(spawns);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onWorldDataBegin() {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)39;
      TEMP_BUFFER.position(0);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void onWorldDataBegin(NetConnection playerConnection) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)39;
      TEMP_BUFFER.position(0);
      packet.writeLength = TEMP_BUFFER.position();
      playerConnection.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void removeQueueBlock(NetConnection playerConnection, int x, int y,
      boolean breaking) {
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)40;
      TEMP_BUFFER.position(0);
      TEMP_BUFFER.putInt(x);
      TEMP_BUFFER.putInt(y);
      TEMP_BUFFER.put(breaking ? (byte)1 : 0);
      packet.writeLength = TEMP_BUFFER.position();
      playerConnection.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void removeTile(Tile tile) {
    if(mindustry.Vars.net.server() || !mindustry.Vars.net.active()) {
      mindustry.world.Tile.removeTile(tile);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)41;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeTile(TEMP_BUFFER, tile);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void sendMessage(String message) {
    if(mindustry.Vars.net.server() || !mindustry.Vars.net.active()) {
      mindustry.core.NetClient.sendMessage(message);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)46;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeString(TEMP_BUFFER, message);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void setTile(Tile tile, Block block, Team team, int rotation) {
    if(mindustry.Vars.net.server() || !mindustry.Vars.net.active()) {
      mindustry.world.Tile.setTile(tile, block, team, rotation);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)50;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeTile(TEMP_BUFFER, tile);
      mindustry.io.TypeIO.writeBlock(TEMP_BUFFER, block);
      mindustry.io.TypeIO.writeTeam(TEMP_BUFFER, team);
      TEMP_BUFFER.putInt(rotation);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.tcp);
    }
  }

  public static synchronized void transferItemTo(Item item, int amount, float x, float y,
      Tile tile) {
    if(mindustry.Vars.net.server() || !mindustry.Vars.net.active()) {
      mindustry.entities.effect.ItemTransfer.transferItemTo(item, amount, x, y, tile);
    }
    if(mindustry.Vars.net.server()) {
      mindustry.net.Packets.InvokePacket packet = arc.util.pooling.Pools.obtain(mindustry.net.Packets.InvokePacket.class, mindustry.net.Packets.InvokePacket::new);
      packet.writeBuffer = TEMP_BUFFER;
      packet.priority = (byte)0;
      packet.type = (byte)54;
      TEMP_BUFFER.position(0);
      mindustry.io.TypeIO.writeItem(TEMP_BUFFER, item);
      TEMP_BUFFER.putInt(amount);
      TEMP_BUFFER.putFloat(x);
      TEMP_BUFFER.putFloat(y);
      mindustry.io.TypeIO.writeTile(TEMP_BUFFER, tile);
      packet.writeLength = TEMP_BUFFER.position();
      mindustry.Vars.net.send(packet, mindustry.net.Net.SendMode.udp);
    }
  }
}