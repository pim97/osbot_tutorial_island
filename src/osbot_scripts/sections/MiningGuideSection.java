package osbot_scripts.sections;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.sections.progress.MiningGuildeSectionProgress;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class MiningGuideSection extends TutorialSection {

	MiningGuildeSectionProgress progress = MiningGuildeSectionProgress.WALK_TO_INSTRUCTOR;

	public MiningGuideSection() {
		super("Mining instructor");
		// TODO Auto-generated constructor stub
	}

	private boolean overwrite = false;;

	@Override
	public void onLoop() throws InterruptedException {

		if (pendingContinue()) {
			selectContinue();
		}

		if (!overwrite) {
			if (getWidgets().containingText("Go through the gate") != null) {
				progress = MiningGuildeSectionProgress.WALKING_TO_GATE;
			} else if (getWidgets().containingText("The smithing menu open") != null) {
				progress = MiningGuildeSectionProgress.CHOOSING_DAGGER_IN_INTERFACE;
			} else if (getWidgets().containingText("Smithing a dagger") != null) {
				progress = MiningGuildeSectionProgress.SMITHING_A_DAGGER;
			} else if (getWidgets().containingText("You've made a bronze bar") != null) {
				progress = MiningGuildeSectionProgress.TALK_WITH_INSTRUCTOR_ABOUT_SMELTING;
			} else if (getWidgets().containingText("both some copper and tin ore") != null) {
				progress = MiningGuildeSectionProgress.SMELTING;
			} else if (getWidgets().containingText("just need some copper ore") != null) {
				progress = MiningGuildeSectionProgress.MINING_COPPER;
			} else if (getWidgets().containingText("Mining") != null) {
				progress = MiningGuildeSectionProgress.MINING_TIN;
			} else if (getWidgets().containingText("It's copper") != null) {
				progress = MiningGuildeSectionProgress.TALK_WITH_INSTRUCTOR_ABOUT_TIN_AND_COPPER;
			} else if (getWidgets().containingText("So you know there's tin") != null) {
				progress = MiningGuildeSectionProgress.PROSPECTING_COPPER;
			} else if (getWidgets().containingText("Go prospect a mineable rock") != null) {
				progress = MiningGuildeSectionProgress.PROSPECTING_TIN;
			}
		}

		if (progress == MiningGuildeSectionProgress.WALK_TO_INSTRUCTOR) {
			Position miningInstructorPosition = new Position(3080, 9506, 0);
			if (miningInstructorPosition != null) {
				if (getWalking().walk(miningInstructorPosition)) {
					progress = MiningGuildeSectionProgress.TALK_WITH_INSTRUCTOR;
				}
			}
		} else if (progress == MiningGuildeSectionProgress.TALK_WITH_INSTRUCTOR) {
			talkAndContinueWithInstructor();
		} else if (progress == MiningGuildeSectionProgress.PROSPECTING_TIN) {
			clickObject(10080, "Prospect");
		} else if (progress == MiningGuildeSectionProgress.PROSPECTING_COPPER) {
			clickObject(10079, "Prospect");
		} else if (progress == MiningGuildeSectionProgress.TALK_WITH_INSTRUCTOR_ABOUT_TIN_AND_COPPER) {
			talkAndContinueWithInstructor();

			if (getTabs().open(Tab.INVENTORY)) {
				progress = MiningGuildeSectionProgress.MINING_TIN;
			}
		} else if (progress == MiningGuildeSectionProgress.MINING_TIN) {
			mineTin();
		} else if (progress == MiningGuildeSectionProgress.MINING_COPPER) {
			mineCopper();
		} else if (progress == MiningGuildeSectionProgress.SMELTING) {
			Item tinOre = getInventory().getItem(438);
			if (tinOre != null) {
				Item copperOre = getInventory().getItem(436);
				if (copperOre != null) {
					if (tinOre.interact()) {
						clickObject(10082, "Use");
						progress = MiningGuildeSectionProgress.TALK_WITH_INSTRUCTOR_ABOUT_SMELTING;
					}
				} else {
					mineCopper();
				}
			} else {
				mineTin();
			}
		} else if (progress == MiningGuildeSectionProgress.TALK_WITH_INSTRUCTOR_ABOUT_SMELTING) {
			talkAndContinueWithInstructor();
		} else if (progress == MiningGuildeSectionProgress.SMITHING_A_DAGGER) {
			setOverwriteToFalse();
			clickObject(2097, "Smith");
		} else if (progress == MiningGuildeSectionProgress.CHOOSING_DAGGER_IN_INTERFACE) {
			RS2Widget daggerWidget = getWidgets().get(312, 2, 2);
			if (daggerWidget != null) {
				if (daggerWidget.interact()) {
					Sleep.sleepUntil(getInventory().contains(1205), 5000, 1000);
					progress = MiningGuildeSectionProgress.WALKING_TO_GATE;
				}
			} else {
				progress = MiningGuildeSectionProgress.SMITHING_A_DAGGER;
				overwrite = true;
			}
		} else if (progress == MiningGuildeSectionProgress.WALKING_TO_GATE) {
			Position gatePosition = new Position(3093, 9503, 0);
			if (!myPlayer().getArea(2).contains(gatePosition)) {
				getWalking().walk(gatePosition);
			} else {
				clickObject(9718, "Open");
			}
		}

	}

	/**
	 * 
	 */
	private void setOverwriteToFalse() {
		if (overwrite) {
			overwrite = false;
		}
	}

	/**
	 * 
	 */
	private void mineCopper() {
		clickObject(10079, "Mine");
	}

	/**
	 * 
	 */
	private void mineTin() {
		clickObject(10080, "Mine");
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
