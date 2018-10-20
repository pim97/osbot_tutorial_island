package osbot_scripts.sections;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TestScript;
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

			if (Sleep.sleepUntil(myPlayer().getArea(2).contains(bankingPosition), 10000, 3000)) {
				
				if (getDialogues().inDialogue()) {
					if (pendingContinue()) {
						selectContinue();
					} else if (getWidgets().containingText("Yes.") != null) {
						if (getDialogues().selectOption(1)) {
							Sleep.sleepUntil(getBank().isOpen(), 3500, 1000);
							if (getBank().close()) {
							}
						}
					}
				} else {
					clickObject(10083, "Use");
				}
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
			} else {
				clickObject(26815, "Use");
			}
			break;
			
		case 525:
			clickObject(9721, "Open");
			break;
			
		case 530:
			talkAndContinueWithInstructor();
			break;
			
		case 531:
			if (getTabs().open(Tab.IGNORES)) {
			}
			break;
			
		case 532:
			talkAndContinueWithInstructor();
			break;
			
		case 540:

			clickObject(9722, "Open");
			break;
			
		case 550:
			clickObject(1521, "Open");
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
		return MainState.CHURCH_GUIDE_SECTION;
	}

}
