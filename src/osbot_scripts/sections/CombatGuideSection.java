package osbot_scripts.sections;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.sections.progress.CombatGuideSectionProgress;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class CombatGuideSection extends TutorialSection {

	private CombatGuideSectionProgress progress = CombatGuideSectionProgress.TALK_WITH_INSTRUCTOR;

	public CombatGuideSection() {
		super("Combat Instructor");
		// TODO Auto-generated constructor stub
	}

	private boolean overwrite = false;

	@Override
	public void onLoop() throws InterruptedException {
		// TODO Auto-generated method stub

		if (!overwrite) {
			if (getWidgets().containingText("Now you have a bow and some arrows") != null) {
				progress = CombatGuideSectionProgress.ATTACK_RAT;
			} else if (getWidgets().containingText("Pass through the gate") != null) {
				progress = CombatGuideSectionProgress.CLICK_GATE_BACK_TO_INSTRUCTOR;
			} else if (getWidgets().containingText("To attack the rat, right click it") != null) {
				progress = CombatGuideSectionProgress.ATTACK_WITH_RAT_SWORD;
			} else if (getWidgets().containingText("This is your combat interface") != null) {
				progress = CombatGuideSectionProgress.WALK_TO_GATE_WITH_MOUSE;
			} else if (getWidgets().containingText("Click on the flashing crossed") != null) {
				progress = CombatGuideSectionProgress.OPEN_COMBAT_TAB;
			} else if (getWidgets().containingText("After you've unequipped your dagger") != null) {
				progress = CombatGuideSectionProgress.EQUIP_SWORD_AND_SHIELD;
			} else if (getWidgets().containingText("You're now holding your dagger") != null) {
				progress = CombatGuideSectionProgress.TALK_INSTRUCTOR_ABOUT_DAGGER;
			} else if (getWidgets().containingText("You can see what items you are weaning") != null) {
				progress = CombatGuideSectionProgress.EQUIP_DAGGER;
			} else if (getWidgets().containingText("This is your worn inventory") != null) {
				progress = CombatGuideSectionProgress.OPEN_EQUIPMENT_STATS;
			} else if (getWidgets().containingText("You now have access to a new interface") != null) {
				progress = CombatGuideSectionProgress.OPEN_EQUIPMENT_TAB;
			}
		}

		if (progress == CombatGuideSectionProgress.TALK_WITH_INSTRUCTOR) {
			talkAndContinueWithInstructor();
		} else if (progress == CombatGuideSectionProgress.OPEN_EQUIPMENT_TAB) {
			if (getTabs().open(Tab.EQUIPMENT)) {
				progress = CombatGuideSectionProgress.OPEN_EQUIPMENT_STATS;
			}
		} else if (progress == CombatGuideSectionProgress.OPEN_EQUIPMENT_STATS) {
			RS2Widget statsWidget = getWidgets().get(387, 18);
			if (statsWidget != null) {
				if (statsWidget.interact()) {
					progress = CombatGuideSectionProgress.EQUIP_DAGGER;
					setOverwriteFalse();
				}
			} else {
				getTabs().open(Tab.EQUIPMENT);
			}
		} else if (progress == CombatGuideSectionProgress.EQUIP_DAGGER) {
			Item dagger = getInventory().getItem(1205);
			if (dagger != null) {
				if (equipmentInterfaceOpen() == null) {
					progress = CombatGuideSectionProgress.OPEN_EQUIPMENT_TAB;
					overwrite = true;
				} else {
					if (closeEquipmentInterface() != null) {
						if (dagger.interact()) {
							if (closeEquipmentInterface().interact()) {
								progress = CombatGuideSectionProgress.TALK_INSTRUCTOR_ABOUT_DAGGER;
							}
						}
					}
				}
			} else {
				log("DAGGER NULL!");
			}
		} else if (progress == CombatGuideSectionProgress.TALK_INSTRUCTOR_ABOUT_DAGGER) {
			talkAndContinueWithInstructor();
		} else if (progress == CombatGuideSectionProgress.EQUIP_SWORD_AND_SHIELD) {
			Item swordAndShield = getInventory().getItem(1277, 1171);
			if (swordAndShield != null) {
				swordAndShield.interact();
			}
		} else if (progress == CombatGuideSectionProgress.OPEN_COMBAT_TAB) {
			if (getTabs().open(Tab.ATTACK)) {
				progress = CombatGuideSectionProgress.WALK_TO_GATE_WITH_MOUSE;
			}
		} else if (progress == CombatGuideSectionProgress.WALK_TO_GATE_WITH_MOUSE) {
			clickObject(9720, "Open");
		} else if (progress == CombatGuideSectionProgress.ATTACK_WITH_RAT_SWORD) {
			if (attackRat()) {
				Sleep.sleepUntil(!myPlayer().isUnderAttack() && myPlayer().isAttackable(), 10000, 3000);
			}
		} else if (progress == CombatGuideSectionProgress.CLICK_GATE_BACK_TO_INSTRUCTOR) {
			if (new Area(new int[][] { { 3101, 9510 }, { 3104, 9510 }, { 3105, 9510 }, { 3107, 9512 }, { 3107, 9513 },
					{ 3108, 9514 }, { 3109, 9514 }, { 3110, 9515 }, { 3110, 9517 }, { 3111, 9518 }, { 3111, 9520 },
					{ 3110, 9521 }, { 3110, 9522 }, { 3109, 9523 }, { 3107, 9523 }, { 3105, 9525 }, { 3104, 9525 },
					{ 3103, 9526 }, { 3101, 9526 }, { 3093, 9525 }, { 3092, 9510 } })
							.contains(getPlayers().myPosition())) {
				clickObject(9719, "Open");
			}
			talkAndContinueWithInstructor();
		} else if (progress == CombatGuideSectionProgress.ATTACK_RAT) {
			if (getTabs().open(Tab.INVENTORY)) {
				if (!getEquipment().isWearingItem(EquipmentSlot.WEAPON, "Shortbow")) {
					wieldItem("Shortbow");
				} else if (!getEquipment().isWearingItem(EquipmentSlot.ARROWS, "Bronze arrow")) {
					wieldItem("Bronze arrow");
				}
				if (attackRat()) {
					Sleep.sleepUntil(!myPlayer().isUnderAttack() && myPlayer().isAttackable(), 10000, 3000);
				}
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private boolean attackRat() {
		NPC rat = getNpcs().closest(npc -> npc.getName().equalsIgnoreCase("Giant rat") && npc.isAttackable());
		if (rat != null) {
			if (rat.interact()) {
				Sleep.sleepUntil(myPlayer().getInteracting() != null, 8000, 3000);
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param name
	 */
	private void wieldItem(String name) {
		if (getInventory().getItem(name).interact("Wield")) {
			Sleep.sleepUntil(getEquipment().contains(name), 1500);
		}
	}

	/**
	 * 
	 */
	private void setOverwriteFalse() {
		if (overwrite) {
			overwrite = false;
		}
	}

	/**
	 * 
	 * @return
	 */
	private RS2Widget closeEquipmentInterface() {
		RS2Widget statsWidget = getWidgets().get(84, 4);
		return statsWidget;
	}

	/**
	 * 
	 * @return
	 */
	private RS2Widget equipmentInterfaceOpen() {
		RS2Widget statsWidget = getWidgets().get(84, 1);
		return statsWidget;
	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MainState getNextMainState() {
		// TODO Auto-generated method stub
		return null;
	}

}
