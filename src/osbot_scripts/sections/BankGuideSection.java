package osbot_scripts.sections;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TutorialScript;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class BankGuideSection extends TutorialSection {

	public BankGuideSection() {
		super("Account Guide");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		log(getProgress());

		switch (getProgress()) {
		case 510:
			Position bankingPosition = new Position(3121, 3123, 0);
			getWalking().walk(bankingPosition);

			Sleep.sleepUntil(myPlayer().getArea(5).contains(bankingPosition), 10000, 3000);

			if (getDialogues().isPendingContinuation()) {
				if (pendingContinue()) {
					selectContinue();
				}
			} else if (getWidgets().containingText("Yes.") != null) {
				if (getDialogues().selectOption(1)) {
					Sleep.sleepUntil(getBank().isOpen(), 3500, 1000);
					if (getBank().close()) {
					}
				}
			} else {
				clickObject(10083, "Use");
			}
			break;

		case 520:
			if (pendingContinue()) {
				selectContinue();
				RS2Widget pollWidget = getWidgets().get(345, 2, 11);
				if (pollWidget != null) {
					if (pollWidget.interact()) {
					}
				}
			}
			break;

		case 525:
			clickObject(9721, "Open");
			break;

		case 530:
			isInInstrucorRoom();
			talkAndContinueWithInstructor();
			break;

		case 531:
			isInInstrucorRoom();
			if (getTabs().open(Tab.IGNORES)) {
			}
			break;

		case 532:
			isInInstrucorRoom();
			talkAndContinueWithInstructor();
			break;

		case 540:
			clickObject(9722, "Open");
			break;

		case 550:
			clickObject(1521, "Open", new Position(3129, 3107, 0));
			TutorialScript.mainState = getNextMainState();
			break;

		}

	}
	
	private void isInInstrucorRoom() {
		if (!new Area(new int[][] { { 3125, 3126 }, { 3130, 3126 }, { 3130, 3122 }, { 3125, 3122 } })
				.contains(getPlayers().myPosition())) {
			clickObject(9721, "Open", new Position(3124, 3124, 0));
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
		return MainState.CHURCH_GUIDE_SECTION;
	}

}
