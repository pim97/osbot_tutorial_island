package osbot_scripts.sections;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class WizardGuideSection extends TutorialSection {


	public WizardGuideSection() {
		super("Magic Instructor");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		log(getProgress());
		
		switch (getProgress()) {
		case 620:
			Position walkTo = new Position(3141, 3086, 0);
			if (myPlayer().getArea(5).contains(walkTo)) {
				talkAndContinueWithInstructor();
			} else {
				if (getWalking().walk(walkTo)) {
					if (Sleep.sleepUntil(myPlayer().getArea(3).contains(walkTo), 10000, 3000)) {
					}
				}
			}
			break;
			
		case 640:
			if (getTabs().open(Tab.MAGIC)) {
				talkAndContinueWithInstructor();
			}
			break;
			
		case 650:
			if (attackChicken()) {
			}
			break;
			
		case 670:
			talkAndContinueWithInstructor();

			if (getDialogues().isPendingOption()) {
				getDialogues().selectOption("Yes.");
				getDialogues().selectOption("No, I'm not planning to do that.");
			}
			break;
		}
	}

	private boolean attackChicken() {
		NPC chicken = getNpcs().closest("Chicken");
		if (chicken != null && getMagic().castSpellOnEntity(Spells.NormalSpells.WIND_STRIKE, chicken)) {
			Sleep.sleepUntil(getProgress() != 650, 3000, 500);
			return true;
		}
		return false;
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
