package osbot_scripts.sections;

import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TestScript;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class GuilinorGuideSection extends TutorialSection {

	public GuilinorGuideSection() {
		super("Gielinor Guide");
	}

	@Override
	public void onLoop() throws InterruptedException {
		log(getProgress());
		
		if (pendingContinue()) {
			selectContinue();
		}

		switch (getProgress()) {
		case 0:
			if (getWidgets().getWidgetContainingText("What's your experience with Old School Runescape?") != null) {
				getDialogues().selectOption(random(1, 3));
			} else {
				talkAndContinueWithInstructor();
			}
			break;

		case 3:
			getTabs().open(Tab.SETTINGS);
			break;

		case 7:
			talkAndContinueWithInstructor();
			break;

		case 10:
			clickObject(9398, "Open");
			Sleep.sleepUntil(!myPlayer().isMoving(), 3000, 1000);
			break;

		case 20:
			TestScript.mainState = getNextMainState();
			break;
		}

	}

	@Override
	public boolean isCompleted() {
		return getProgress() > 0 ? true : false;
	}

	@Override
	public MainState getNextMainState() {
		// TODO Auto-generated method stub
		return MainState.SURVIVAL_EXPERT;
	}

}
