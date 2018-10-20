package osbot_scripts.sections;

import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TestScript;
import osbot_scripts.sections.total.progress.MainState;

public class QuestGuideSection extends TutorialSection {

	public QuestGuideSection() {
		super("Quest Guide");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLoop() throws InterruptedException {

		log(getProgress());
		
		switch (getProgress()) {
		case 220:
			talkAndContinueWithInstructor();
			break;
			
		case 230:
			getTabs().open(Tab.QUEST);
			break;
			
		case 240:
			talkAndContinueWithInstructor();
			break;
			
		case 250:
			clickObject(9726, "Climb-down");
			break;
			
		case 260:
			TestScript.mainState = getNextMainState();
			break;
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
		return MainState.MINING_SECTION;
	}

}
