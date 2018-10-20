package osbot_scripts.sections;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TestScript;
import osbot_scripts.sections.progress.ChurchGuideSectionProgress;
import osbot_scripts.sections.total.progress.MainState;

public class ChurchGuideSection extends TutorialSection {

	ChurchGuideSectionProgress progress = ChurchGuideSectionProgress.CLICK_DOOR_IF_CLOSED;

	public ChurchGuideSection() {
		super("Brother Brace");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		log(progress);
		log(getProgress());

		switch (getProgress()) {
		case 550:
			if (new Area(new int[][] { { 3129, 3111 }, { 3129, 3103 }, { 3120, 3103 }, { 3120, 3111 } })
					.contains(myPlayer().getPosition())) {
				talkAndContinueWithInstructor();
			} else {
				clickObject(1521, "Open");
				talkAndContinueWithInstructor();
			}
			break;

		case 560:
			getTabs().open(Tab.PRAYER);
			break;

		case 570:
			talkAndContinueWithInstructor();
			break;

		case 580:
			getTabs().open(Tab.FRIENDS);
			break;

		case 600:
			talkAndContinueWithInstructor();
			break;

		case 610:
			clickObject(9723, "Open");

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
		return MainState.WIZARD_GUIDE_SECTION;
	}

}
