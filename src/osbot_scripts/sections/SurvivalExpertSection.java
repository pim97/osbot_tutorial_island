package osbot_scripts.sections;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TestScript;
import osbot_scripts.sections.progress.SurvivalExpertSectionProgress;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class SurvivalExpertSection extends TutorialSection {

	private SurvivalExpertSectionProgress progress = SurvivalExpertSectionProgress.TALKING_ONE;

	private boolean again = false;

	public SurvivalExpertSection() {
		super("Survival Expert");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLoop() throws InterruptedException {
		log(progress);

		if (!again) {
			if (getWidgets().containingText("Click on the gate shown and follow the path") != null) {
				progress = SurvivalExpertSectionProgress.END_SECTION_GATE;
			} else if (getWidgets().containingText("Now you have caught some shrimp, let's cook it") != null) {
				progress = SurvivalExpertSectionProgress.COOKING_SHRIMP;
			} else if (getWidgets().containingText("Click on the sparkling fishing spot") != null) {
				progress = SurvivalExpertSectionProgress.FISHING_FISH;
			} else if (getWidgets().containingText("how good your skills are") != null) {
				progress = SurvivalExpertSectionProgress.TALKING_TWO;
			} else if (getWidgets().containingText("You gained some experience") != null) {
				progress = SurvivalExpertSectionProgress.OPEN_SKILLS_TAB;
			} else if (getWidgets().containingText("You managed to cut some logs from the tree") != null) {
				progress = SurvivalExpertSectionProgress.MAKING_FIRE;
			} else if (getWidgets().containingText("Use this to get some logs by clicking") != null) {
				progress = SurvivalExpertSectionProgress.CUTTING_LOGS;
			} else if (getWidgets().containingText("Click on the flashing backpack icon to the right") != null) {
				progress = SurvivalExpertSectionProgress.CLICK_INVENTORY;
			} else if (getWidgets().containingText("Talk to the Survival Expert by the pond") != null) {
				progress = SurvivalExpertSectionProgress.TALKING_ONE;
			}
		}

		if (progress == SurvivalExpertSectionProgress.TALKING_ONE) {
			if (!pendingContinue()) {
				talkToConstructor();
			} else if (pendingContinue()) {
				selectContinue();
			}
		} else if (progress == SurvivalExpertSectionProgress.CLICK_INVENTORY) {
			getTabs().open(Tab.INVENTORY);
		} else if (progress == SurvivalExpertSectionProgress.CUTTING_LOGS) {
			RS2Object tree = getObjects().closest(9730);
			if (tree != null && tree.isVisible()) {
				tree.interact("Chop down");

				Sleep.sleepUntil(myPlayer().getAnimation() == -1, 10000, 1000);
			}

			if ((progress == SurvivalExpertSectionProgress.FISHING_FISH
					|| progress == SurvivalExpertSectionProgress.CUTTING_LOGS) && again) {
				progress = SurvivalExpertSectionProgress.COOKING_SHRIMP;
				again = false;
			}

		} else if (progress == SurvivalExpertSectionProgress.MAKING_FIRE) {
			makingFire();
		} else if (progress == SurvivalExpertSectionProgress.OPEN_SKILLS_TAB) {
			Sleep.sleepUntil(getTabs().open(Tab.SKILLS), 10000, 1000);
		} else if (progress == SurvivalExpertSectionProgress.TALKING_TWO) {
			if (!pendingContinue()) {
				talkToConstructor();
			} else if (pendingContinue()) {
				selectContinue();
			}
		} else if (progress == SurvivalExpertSectionProgress.FISHING_FISH) {
			NPC fishingSpot = getNpcs().closest("Fishing spot");
			log("fishspot" + fishingSpot);
			if (fishingSpot != null && fishingSpot.isVisible()) {
				if (fishingSpot.interact("Net")) {

					progress = SurvivalExpertSectionProgress.COOKING_SHRIMP;

					Sleep.sleepUntil(!myPlayer().isMoving() && myPlayer().getAnimation() == -1, 10000, 5000);
				}
			}

			if ((progress == SurvivalExpertSectionProgress.FISHING_FISH
					|| progress == SurvivalExpertSectionProgress.CUTTING_LOGS) && again) {
				progress = SurvivalExpertSectionProgress.COOKING_SHRIMP;
				again = false;
			}

		} else if (progress == SurvivalExpertSectionProgress.COOKING_SHRIMP) {
			if (!getInventory().contains(2514)) {
				progress = SurvivalExpertSectionProgress.FISHING_FISH;
				again = true;
			} else if (!getInventory().contains(2511)) {
				progress = SurvivalExpertSectionProgress.CUTTING_LOGS;
				again = true;
			}
			// Missing logs or raw shrimp
			if (getInventory().contains(2514, 2511) && !again) {
				makingFire();
				useShrimpOnFire();
			}
			// Burned shrimp
			if (getInventory().contains(7954) && !again) {
				getInventory().dropAll(7954);
			}
			// Cooked shrimp
			if (getInventory().contains(315)) {
				progress = SurvivalExpertSectionProgress.END_SECTION_GATE;
			}
		} else if (progress == SurvivalExpertSectionProgress.END_SECTION_GATE) {
			Position doorPosition = new Position(3091, 3092, 0);
			getWalking().walk(doorPosition);

			if (isInPosition(doorPosition)) {
				RS2Object doorObject = getObjects().closest(9708, 9470);

				if (doorObject != null && doorObject.isVisible()) {
					doorObject.interact("Open");
					TestScript.mainState = getNextMainState();
				}
			}
		}

	}

	/**
	 * 
	 */
	private void useShrimpOnFire() {
		Item shrimp = getInventory().getItem("Raw shrimps");
		if (shrimp != null) {
			RS2Object fire = getObjects().closest("Fire");
			if (fire != null && fire.isVisible()) {
				if (shrimp.interact("Use")) {
					if (fire.interact("Use")) {

						Sleep.sleepUntil(myPlayer().getAnimation() == -1, 10000, 5000);
					}
				}
			}
		}
	}

	/**
	 * 
	 */
	private void makingFire() {
		Item tinderbox = getInventory().getItem("Tinderbox");
		if (tinderbox != null) {
			Item log = getInventory().getItem("Logs");

			if (log != null && tinderbox.interact("Use")) {
				if (log.interact("Use")) {
					Sleep.sleepUntil(myPlayer().getAnimation() == -1, 10000, 5000);

				}
			}
		}
	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MainState getNextMainState() {
		// TODO Auto-generated method stub
		return MainState.COOKING_GUIDE_SECTION;
	}

}
