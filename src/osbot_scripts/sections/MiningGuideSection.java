package osbot_scripts.sections;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TestScript;
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
		log(getProgress());
		log(progress);
		
		switch (getProgress()) {
		case 260:
			Position miningInstructorPosition = new Position(3080, 9506, 0);
			if (miningInstructorPosition != null) {
				if (getWalking().walk(miningInstructorPosition)) {
					progress = MiningGuildeSectionProgress.TALK_WITH_INSTRUCTOR;
				}
			}
			if (myPlayer().getArea(5).contains(miningInstructorPosition)) {
				talkAndContinueWithInstructor();
			}
			break;
			
		case 270:
			clickObject(10080, "Prospect");
			break;
			
		case 280:
			clickObject(10079, "Prospect");
			break;
			
		case 290:
			talkAndContinueWithInstructor();

			if (getTabs().open(Tab.INVENTORY)) {
				progress = MiningGuildeSectionProgress.TALK_WITH_INSTRUCTOR_ABOUT_TIN_AND_COPPER;
			}
			break;
			
		case 300:
			mineTin();
			progress = MiningGuildeSectionProgress.MINING_TIN;
			break;
			
		case 310:
			progress = MiningGuildeSectionProgress.MINING_COPPER;
			mineCopper();
			break;
			
		case 320:
			Item tinOre = getInventory().getItem(438);
			if (tinOre != null) {
				Item copperOre = getInventory().getItem(436);
				if (copperOre != null) {
					if (tinOre.interact()) {
						clickObject(10082, "Use");
						progress = MiningGuildeSectionProgress.SMELTING;
					}
				} else {
					mineCopper();
				}
			} else {
				mineTin();
			}
			break;
			
		case 330:
			progress = MiningGuildeSectionProgress.TALK_WITH_INSTRUCTOR_ABOUT_SMELTING;
			talkAndContinueWithInstructor();
			break;
			
		case 340:
			clickObject(2097, "Smith", new Position(3082, 9499, 0));
			break;
			
		case 350:
			RS2Widget daggerWidget = getWidgets().get(312, 2, 2);
			if (daggerWidget != null) {
				if (daggerWidget.interact()) {
					Sleep.sleepUntil(getInventory().contains(1205), 5000, 1000);
				}
			}
			break;
			
		case 360:
			Position gatePosition = new Position(3093, 9503, 0);
			if (!myPlayer().getArea(2).contains(gatePosition)) {
				getWalking().walk(gatePosition);
			} else {
				clickObject(9718, "Open");
				TestScript.mainState = getNextMainState();
			}
			break;
			

		case 370:
			TestScript.mainState = getNextMainState();
			break;

		default:
			break;
		}

		if (pendingContinue()) {
			selectContinue();
		}

//		if (!overwrite) {
//			if (getWidgets().containingText("Go through the gate") != null) {
//				progress = MiningGuildeSectionProgress.WALKING_TO_GATE;
//			} else if (getWidgets().containingText("The smithing menu open") != null) {
//				progress = MiningGuildeSectionProgress.CHOOSING_DAGGER_IN_INTERFACE;
//			} else if (getWidgets().containingText("Smithing a dagger") != null) {
//				progress = MiningGuildeSectionProgress.SMITHING_A_DAGGER;
//			} else if (getWidgets().containingText("You've made a bronze bar") != null) {
//				progress = MiningGuildeSectionProgress.TALK_WITH_INSTRUCTOR_ABOUT_SMELTING;
//			} else if (getWidgets().containingText("both some copper and tin ore") != null) {
//				progress = MiningGuildeSectionProgress.SMELTING;
//			} else if (getWidgets().containingText("just need some copper ore") != null) {
//				progress = MiningGuildeSectionProgress.MINING_COPPER;
//			} else if (getWidgets().containingText("Mining") != null) {
//				progress = MiningGuildeSectionProgress.MINING_TIN;
//			} else if (getWidgets().containingText("It's copper") != null) {
//				progress = MiningGuildeSectionProgress.TALK_WITH_INSTRUCTOR_ABOUT_TIN_AND_COPPER;
//			} else if (getWidgets().containingText("So you know there's tin") != null) {
//				progress = MiningGuildeSectionProgress.PROSPECTING_COPPER;
//			} else if (getWidgets().containingText("Go prospect a mineable rock") != null) {
//				progress = MiningGuildeSectionProgress.PROSPECTING_TIN;
//			}
//		}

	
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
		return MainState.COMBAT_SECTION;
	}

}
