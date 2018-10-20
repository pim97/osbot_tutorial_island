package osbot_scripts.sections;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TutorialScript;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class MiningGuideSection extends TutorialSection {

	public MiningGuideSection() {
		super("Mining instructor");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLoop() throws InterruptedException {
		log(getProgress());
		
		switch (getProgress()) {
		case 260:
			Position miningInstructorPosition = new Position(3080, 9506, 0);
			if (miningInstructorPosition != null) {
				if (getWalking().walk(miningInstructorPosition)) {
				}
			}
			if (myPlayer().getArea(5).contains(miningInstructorPosition)) {
				talkAndContinueWithInstructor();
			}
			break;
			
		case 270:
			clickObject(10080, "Prospect", new Position(3076, 9502, 0));
			break;
			
		case 280:
			clickObject(10079, "Prospect", new Position(3084, 9502, 0));
			break;
			
		case 290:
			talkAndContinueWithInstructor();

			if (getTabs().open(Tab.INVENTORY)) {
			}
			break;
			
		case 300:
			mineTin();
			break;
			
		case 310:
			mineCopper();
			break;
			
		case 320:
			Item tinOre = getInventory().getItem(438);
			if (tinOre != null) {
				Item copperOre = getInventory().getItem(436);
				if (copperOre != null) {
					if (tinOre.interact()) {
						clickObject(10082, "Use", new Position(3079, 9498, 0));
					}
				} else {
					mineCopper();
				}
			} else {
				mineTin();
			}
			break;
			
		case 330:
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
				TutorialScript.mainState = getNextMainState();
			}
			break;
			

		case 370:
			TutorialScript.mainState = getNextMainState();
			break;
		}

		if (pendingContinue()) {
			selectContinue();
		}

	
	}
	/**
	 * 
	 */
	private void mineCopper() {
		clickObject(10079, "Mine", new Position(3084, 9502, 0));
	}

	/**
	 * 
	 */
	private void mineTin() {
		clickObject(10080, "Mine", new Position(3076, 9502, 0));
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
