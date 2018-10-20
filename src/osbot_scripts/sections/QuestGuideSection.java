package osbot_scripts.sections;

import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TestScript;
import osbot_scripts.sections.progress.QuestGuideSectionProgress;
import osbot_scripts.sections.total.progress.MainState;

public class QuestGuideSection extends TutorialSection {

	QuestGuideSectionProgress progress = QuestGuideSectionProgress.TALK_WITH_QUEST_GUIDE;

	public QuestGuideSection() {
		super("Quest Guide");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLoop() throws InterruptedException {

		log(progress);
		log(getProgress());
		
		switch (getProgress()) {
		case 220:
			progress = QuestGuideSectionProgress.TALK_WITH_QUEST_GUIDE;
			talkAndContinueWithInstructor();
			break;
			
		case 230:
			progress = QuestGuideSectionProgress.OPEN_QUEST_TAB;
			getTabs().open(Tab.QUEST);
			break;
			
		case 240:
			progress = QuestGuideSectionProgress.TALK_WITH_QUEST_GUIDE_2;
			talkAndContinueWithInstructor();
			break;
			
		case 250:
			progress = QuestGuideSectionProgress.DOWN_STAIRCASE;
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
