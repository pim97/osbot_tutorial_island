package osbot_scripts.sections;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;

import osbot_scripts.TestScript;
import osbot_scripts.sections.progress.GuilinorGuideSectionProgress;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class GuilinorGuideSection extends TutorialSection {

	private GuilinorGuideSectionProgress progress = GuilinorGuideSectionProgress.TALKING_ONE;

	public GuilinorGuideSection() {
		super("Gielinor Guide");
	}

	@Override
	public void onLoop() throws InterruptedException {
		log(progress);

		// Otherwise can be stuck
		if (getWidgets().getWidgetContainingText("Player controls") != null) {
			progress = GuilinorGuideSectionProgress.CLIKING_WRENCH;
		} else if (getWidgets().getWidgetContainingText("On the side panel, you can now see") != null) {
			progress = GuilinorGuideSectionProgress.TALKING_TWO;
		} else if (getWidgets().getWidgetContainingText("You can interact with many items of scenery") != null) {
			progress = GuilinorGuideSectionProgress.CLICKING_DOOR;
		} else if (isCompleted()) {
			TestScript.mainState = getNextMainState();
		}

		if (progress == GuilinorGuideSectionProgress.TALKING_ONE) {
			// Not talking with constructor? then go talk
			if (!pendingContinue()) {
				talkToConstructor();

				// Has dialogue option?
			} else if (getWidgets()
					.getWidgetContainingText("What's your experience with Old School Runescape?") != null) {
				getDialogues().selectOption(random(1, 3));
				progress = GuilinorGuideSectionProgress.CLIKING_WRENCH;

				// Select to continue if can continue
			} else if (pendingContinue()) {
				selectContinue();
			}

		} else if (progress == GuilinorGuideSectionProgress.CLIKING_WRENCH) {
			// At clicking wrench? Open it
			RS2Widget settingsTab = getWidgets().get(548, 42);
			if (settingsTab != null) {
				settingsTab.interact("Options");
				progress = GuilinorGuideSectionProgress.TALKING_TWO;
			}
		} else if (progress == GuilinorGuideSectionProgress.TALKING_TWO) {
			if (!pendingContinue()) {
				talkToConstructor();
				// Select to continue if can continue
			} else if (pendingContinue()) {
				selectContinue();
			}
		} else if (progress == GuilinorGuideSectionProgress.CLICKING_DOOR) {
			RS2Object doorObject = getObjects().closest("Door");
			
			if (doorObject != null && doorObject.interact("Open")) {
				Sleep.sleepUntil(!myPlayer().isMoving(), 3000, 1000);
			}
		}
	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		if (getWidgets().containingText("Talk to the Survival Expert by the pond") != null) {
			return true;
		}
		return false;
	}

	@Override
	public MainState getNextMainState() {
		// TODO Auto-generated method stub
		return MainState.SURVIVAL_EXPERT;
	}

}
