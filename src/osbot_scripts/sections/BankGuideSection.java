package osbot_scripts.sections;

import java.util.Arrays;
import java.util.List;

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

	private static final Area BANK_AREA = new Area(new int[][] { { 3125, 3121 }, { 3126, 3121 }, { 3126, 3119 },
			{ 3118, 3119 }, { 3118, 3121 }, { 3119, 3121 }, { 3119, 3123 }, { 3115, 3123 }, { 3115, 3128 },
			{ 3118, 3128 }, { 3118, 3126 }, { 3122, 3126 }, { 3122, 3130 }, { 3126, 3130 }, { 3126, 3128 },
			{ 3128, 3128 }, { 3128, 3126 }, { 3130, 3126 }, { 3130, 3123 }, { 3125, 3123 }, { 3125, 3121 } });

	private static final List<Position> PATH_TO_BANK = Arrays.asList(new Position(3111, 3123, 0),
			new Position(3114, 3119, 0), new Position(3118, 3116, 0), new Position(3121, 3118, 0));

	@Override
	public void onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		log(getProgress());

		switch (getProgress()) {
		case 510:
			if (!BANK_AREA.contains(myPosition())) {
				if (getWalking().walkPath(PATH_TO_BANK)) {
					getDoorHandler().handleNextObstacle(BANK_AREA);
				}
			} else if (pendingContinue()) {
				selectContinue();
			} else if (getDialogues().isPendingOption()) {
				getDialogues().selectOption("Yes.");
			} else if (getObjects().closest("Bank booth").interact("Use")) {
				Sleep.sleepUntil(pendingContinue(), 5000, 500);
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
			} else if (getBank().isOpen()) {
				getBank().close();
			} else {
				clickObject(26815, "Use");
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
