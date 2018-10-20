package osbot_scripts.sections;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TestScript;
import osbot_scripts.sections.progress.CombatGuideSectionProgress;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class CombatGuideSection extends TutorialSection {

	private CombatGuideSectionProgress progress = CombatGuideSectionProgress.TALK_WITH_INSTRUCTOR;

	public CombatGuideSection() {
		super("Combat Instructor");
		// TODO Auto-generated constructor stub
	}

	private Position instructorPosition = new Position(3107, 9510, 0);

	@Override
	public void onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		log(progress);
		log(getProgress());

		switch (getProgress()) {
		case 370:
			if (!myPlayer().getArea(5).contains(getInstructorPosition())) {
				getWalking().walk(getInstructorPosition());
			} else {
				talkAndContinueWithInstructor();
			}
			progress = CombatGuideSectionProgress.TALK_WITH_INSTRUCTOR;
			break;

		case 390:
			if (getTabs().open(Tab.EQUIPMENT)) {
				progress = CombatGuideSectionProgress.OPEN_EQUIPMENT_STATS;
			}
			break;

		case 400:
			RS2Widget statsWidget = getWidgets().get(387, 18);
			if (statsWidget != null) {
				if (statsWidget.interact()) {
					progress = CombatGuideSectionProgress.EQUIP_DAGGER;
				}
			} else {
				getTabs().open(Tab.EQUIPMENT);
			}
			break;

		case 405:
			Item dagger = getInventory().getItem(1205);
			if (dagger != null) {
				if (equipmentInterfaceOpen() == null) {
					progress = CombatGuideSectionProgress.OPEN_EQUIPMENT_TAB;
				} else {
					if (closeEquipmentInterface() != null) {
						if (dagger.interact()) {
							if (closeEquipmentInterface() != null && closeEquipmentInterface().interact()) {
								progress = CombatGuideSectionProgress.TALK_INSTRUCTOR_ABOUT_DAGGER;
							}
						}
					}
				}
			} else {
				log("DAGGER NULL!");
			}
			break;

		case 410:
			talkAndContinueWithInstructor();
			break;

		case 420:
			Item swordAndShield = getInventory().getItem(1277, 1171);
			if (swordAndShield != null) {
				swordAndShield.interact();
			}
			break;

		case 430:
			getTabs().open(Tab.ATTACK);
			break;

		case 440:
			walkInto();
			break;

		case 450:
			walkInto();
			if (attackRat()) {
				Sleep.sleepUntil(!myPlayer().isUnderAttack() && myPlayer().isAttackable(), 10000, 3000);
			}
			break;

		case 470:
			walkOut();
			if (!myPlayer().getArea(5).contains(getInstructorPosition())) {
				getWalking().walk(getInstructorPosition());
			} else {
				talkAndContinueWithInstructor();
			}
			break;

		case 490:
		case 480:
			if (getTabs().open(Tab.INVENTORY)) {
				if (!getEquipment().isWearingItem(EquipmentSlot.WEAPON, "Shortbow")) {
					wieldItem("Shortbow");
				} else if (!getEquipment().isWearingItem(EquipmentSlot.ARROWS, "Bronze arrow")) {
					wieldItem("Bronze arrow");
				}
				if (attackRat()) {
					Sleep.sleepUntil(!myPlayer().isUnderAttack() && myPlayer().isAttackable(), 25000, 3000);
				}
			}
			break;

		case 500:
			clickObject(9727, "Climb-up", new Position(3111, 9525, 0));
			break;

		case 510:
			TestScript.mainState = getNextMainState();
			break;
		}

	}

	/**
	 * 
	 * @return
	 */
	private void walkInto() {
		if (!new Area(new int[][] { { 3101, 9510 }, { 3104, 9510 }, { 3105, 9510 }, { 3107, 9512 }, { 3107, 9513 },
				{ 3108, 9514 }, { 3109, 9514 }, { 3110, 9515 }, { 3110, 9517 }, { 3111, 9518 }, { 3111, 9520 },
				{ 3110, 9521 }, { 3110, 9522 }, { 3109, 9523 }, { 3107, 9523 }, { 3105, 9525 }, { 3104, 9525 },
				{ 3103, 9526 }, { 3101, 9526 }, { 3093, 9525 }, { 3092, 9510 } }).contains(getPlayers().myPosition())) {
			clickObject(9720, "Open", new Position(3111, 9519, 0));
		}
	}

	private void walkOut() {
		if (new Area(new int[][] { { 3101, 9510 }, { 3104, 9510 }, { 3105, 9510 }, { 3107, 9512 }, { 3107, 9513 },
				{ 3108, 9514 }, { 3109, 9514 }, { 3110, 9515 }, { 3110, 9517 }, { 3111, 9518 }, { 3111, 9520 },
				{ 3110, 9521 }, { 3110, 9522 }, { 3109, 9523 }, { 3107, 9523 }, { 3105, 9525 }, { 3104, 9525 },
				{ 3103, 9526 }, { 3101, 9526 }, { 3093, 9525 }, { 3092, 9510 } }).contains(getPlayers().myPosition())) {
			clickObject(9719, "Open");
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
		return MainState.BANKING_AREA_SECTION;
	}

	/**
	 * @return the instructorPosition
	 */
	public Position getInstructorPosition() {
		return instructorPosition;
	}

	/**
	 * @param instructorPosition
	 *            the instructorPosition to set
	 */
	public void setInstructorPosition(Position instructorPosition) {
		this.instructorPosition = instructorPosition;
	}

}
