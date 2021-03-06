package com.example.myapplicationtest1.game.contents.unit;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.paint.dot.DotPaint;
import com.example.myapplicationtest1.game.preset.unit.Unit;
import com.example.myapplicationtest1.utils.Cache;

public class Knowledge extends MyUnit {
	public final Cache.OwnCard ownCard;
	public final DotPaint standPaint;
	
	private Knowledge(Cache.OwnCard ownCard, DotPaint additionalStandImage) {
		super(ownCard.card.cardName, ownCard.card.drawableId, FRIEND);
		standPaint = additionalStandImage;
		this.ownCard = ownCard;
	}
	public Knowledge(Cache.OwnCard ownCard) {
		super(ownCard.card.cardName, ownCard.card.drawableId, FRIEND);
		standPaint = getDotPaint();
		this.ownCard = ownCard;
	}
	@Override
	public Knowledge respawn(int x, int y) {
		super.respawn(x, y);
		maxHP = hp = ownCard.healthPoint();
		atk = ownCard.attack();
		def = ownCard.defense();
		atkRange = ownCard.attackRange();
		cd = ownCard.cd();
		spd = ownCard.speed();
		return this;
	}
	@Override
	public void idle() {
		super.idle();
		Unit targetUnit = null;
		double distance = Integer.MAX_VALUE;
		final boolean IS_DMG = atk > 0;
		for(Unit unit : GHQ.stage().units) {
			if(unit != this && (IS_DMG ? unit instanceof Enemy : unit instanceof Knowledge)) {
				final double DISTANCE = unit.point().distance(this);
				if(DISTANCE < distance) {
					distance = DISTANCE;
					targetUnit = unit;
				}
			}
		}
		if(targetUnit != null) {
			angle().set(point().angleTo(targetUnit));
			if(distance > (int)(atkRange*0.8))
				point().approach(targetUnit, spd);
			if(distance < atkRange) {
				mainWeapon.trigger(this);
			}
		}
	}
//	public static class KnowledgeParameter {
//		private static final ImageFrame
//			MAT_N2_IF = ImageFrame.create(R.drawable.greensquare),
//			MAT_SER_IF1 = ImageFrame.create(R.drawable.fenlei1),
//			MAT_SER_IF2 = ImageFrame.create(R.drawable.fenlei2);
//		Knowledge generate() {
//			final Knowledge UNIT;
//			if(this == MAT_N2) { //长*宽*高
//				UNIT = new Knowledge(this);
//				UNIT.mainWeapon = new Weapon() {
//					{
//						this.setCoolTime(0);
//						this.setMagazineSize(60);
//						this.setReloadSpeed(UNIT.cd);
//					}
//					@Override
//					public List<Bullet> setBullets(GHQObject shooter, HitGroup standpoint) {
//						MAT_N2_IF.dotPaint_capSize(shooter.point(), 200);
//						for(Unit unit : GHQ.stage().units) {
//							if(unit instanceof Enemy)
//								((MyUnit)unit).damage_amount(UNIT.atk);
//						}
//						return null;
//					}
//				};
//			}else if(this == MAT_SER) { //分类讨论
//				UNIT = new Knowledge(this);
//				UNIT.mainWeapon = new Weapon() {
//					{
//						this.setCoolTime(UNIT.cd);
//					}
//					@Override
//					public List<Bullet> setBullets(GHQObject shooter, HitGroup standpoint) {
//						MAT_N2_IF.dotPaint_capSize(shooter.point(), 200);
//						double distance1 = Integer.MAX_VALUE, distance2 = Integer.MAX_VALUE;
//						MyUnit assume1 = null, assume2 = null;
//						for(Unit unit : GHQ.stage().units) {
//							if(unit instanceof Enemy) {
//								final double DISTANCE = UNIT.point().distance(unit);
//								if(DISTANCE > MAT_SER.iniATKRange)
//									continue;
//								if(assume1 == null || distance1 > DISTANCE) {
//									assume2 = assume1;
//									distance2 = distance1;
//									assume1 = (MyUnit)unit;
//									distance1 = DISTANCE;
//								}else if(assume2 == null || distance2 > DISTANCE) {
//									assume2 = (MyUnit)unit;
//									distance2 = DISTANCE;
//								}
//							}
//						}
//						if(assume1 != null) {
//							if(assume2 != null) {
//								MAT_SER_IF1.dotPaint(assume1.point());
//								MAT_SER_IF2.dotPaint(assume2.point());
//								assume1.damage_amount(UNIT.atk);
//								assume2.damage_amount(UNIT.atk);
//							}else {
//								MAT_SER_IF1.dotPaint(assume1.point());
//								MAT_SER_IF2.dotPaint(assume1.point());
//								assume1.damage_amount(UNIT.atk*3);
//							}
//						}
//						return null;
//					}
//				};
//			}else if(this == CHI_SER) {
//				UNIT = new Knowledge(this){
//					@Override
//					public int damage_amount(int dmg) {
//						final int REAL_DMG = super.damage_amount(dmg);
//						//生命值上限增加20,攻击力增加2,移动速度增加0.1
//						maxHP += 20; atk += 2; spd += 0.1;
//						return REAL_DMG;
//					}
//				};
//			}else if(this == ENG_SER) {
//				//在攻击敌方单位的同时为攻击范围内我方受伤最为严重的单位恢复相同的数值, 每一次攻击在造成伤害以外还会偷取敌方单位5点攻击力与20点血量上限加到被治疗的我方单位身上。
//				UNIT = new Knowledge(this);
//				UNIT.mainWeapon = new Weapon() {
//					{
//						this.setCoolTime(UNIT.cd);
//					}
//					@Override
//					public List<Bullet> setBullets(GHQObject shooter, HitGroup standpoint) {
//						final MyUnit TARGET = (MyUnit)GHQ.stage().getNearstEnemy(UNIT);
//						if(TARGET != null && UNIT.point().distance(TARGET) < UNIT.atkRange) {
//							final int DMG = TARGET.damage_amount(UNIT.atk);
//							final int ATK_DRAIN = Math.min(TARGET.atk, 5);
//							TARGET.atk -= ATK_DRAIN;
//							final int HP_DRAIN = TARGET.drain_maxHP(UNIT.atk);
//							if(DMG > 0) {
//								final MyUnit HEAL_TARGET = (MyUnit)GHQ.stage().getNearstEnemy(new HitGroup(ENEMY), UNIT.point());
//								if(HEAL_TARGET != null && UNIT.point().distance(HEAL_TARGET) < UNIT.atkRange) {
//									HEAL_TARGET.atk += ATK_DRAIN;
//									HEAL_TARGET.maxHP += HP_DRAIN;
//									HEAL_TARGET.damage_amount(-DMG);
//								}
//							}
//						}
//						return null;
//					}
//				};
//			}else
//				UNIT = new Knowledge(this););
}
