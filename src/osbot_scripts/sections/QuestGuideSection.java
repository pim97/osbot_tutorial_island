package osbot_scripts.sections;

import org.osbot.rs07.api.ui.Tab;

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

		if (getWidgets().containingText("Moving on") != null) {
			progress = QuestGuideSectionProgress.DOWN_STAIRCASE;
		} else if (getWidgets().containingText("Your Quest Journal") != null) {
			progress = QuestGuideSectionProgress.TALK_WITH_QUEST_GUIDE_2;
		} else if (getWidgets().containingText("Open the Quest Journal") != null) {
			progress = QuestGuideSectionProgress.OPEN_QUEST_TAB;
		}

		if (progress == QuestGuideSectionProgress.TALK_WITH_QUEST_GUIDE) {
			talkAndContinueWithInstructor();
		} else if (progress == QuestGuideSectionProgress.OPEN_QUEST_TAB) {
			if (getTabs().open(Tab.QUEST)) {
				progress = QuestGuideSectionProgress.TALK_WITH_QUEST_GUIDE_2;
			}
		} else if (progress == QuestGuideSectionProgress.TALK_WITH_QUEST_GUIDE_2) {
			talkAndContinueWithInstructor();
		} else if (progress == QuestGuideSectionProgress.DOWN_STAIRCASE) {
			clickObject(9726, "Climb-down");
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
		return null;
	}

}
