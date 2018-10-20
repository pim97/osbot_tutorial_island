package osbot_scripts.sections;

import java.awt.event.KeyEvent;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public abstract class TutorialSection extends MethodProvider {

	/**
	 * The name of the instructor of the current stage
	 */
	private final String instructorName;

	/**
	 * 
	 * @param instructorName
	 */
	public TutorialSection(final String instructorName) {
		this.instructorName = instructorName;
	}
	
	/**
	 * Clicking an object
	 * 
	 * @param objectId
	 * @param interactName
	 */
	public void clickObject(int objectId, String interactName) {
		RS2Object rs2Object = getObjects().closest(objectId);
		if (rs2Object != null && rs2Object.isVisible()) {
			rs2Object.interact(interactName);
			Sleep.sleepUntil(myPlayer().getArea(2).contains(rs2Object.getPosition()), 10000, 2000);
		} else if (rs2Object != null && !rs2Object.isVisible()) {
			getWalking().walk(rs2Object.getPosition());
		}
	}
	
	/**
	 * Gets current progress
	 * @return
	 */
	protected final int getProgress() {
        return getConfigs().get(281);
    }
	
	/**
	 * Clicking an object
	 * 
	 * @param objectId
	 * @param interactName
	 */
	public void clickObject(int objectId, String interactName, Position walkTo) {
		RS2Object rs2Object = getObjects().closest(objectId);
		if (rs2Object != null && rs2Object.isVisible()) {
			rs2Object.interact(interactName);
			Sleep.sleepUntil(myPlayer().getArea(2).contains(rs2Object.getPosition()), 10000, 2000);
		} else if (rs2Object != null && !rs2Object.isVisible()) {
			getWalking().walk(walkTo);
		} else if (rs2Object == null) {
			getWalking().walk(walkTo);
		}
	}
	
	/**
	 * 
	 */
	public void talkAndContinueWithInstructor() {
		enableRunning();
		
		if (!pendingContinue()) {
			talkToConstructor();
			// Select to continue if can continue
		} else if (pendingContinue()) {
			selectContinue();
		}
	}

	/**
	 * Loops through the section
	 * 
	 * @throws InterruptedException
	 */
	public abstract void onLoop() throws InterruptedException;

	/**
	 * The section has been completed
	 * 
	 * @return
	 */
	public abstract boolean isCompleted();

	/**
	 * Returns the next main state
	 * 
	 * @return
	 */
	public abstract MainState getNextMainState();

	/**
	 * Returns the constructor of the area
	 * 
	 * @return
	 */
	protected NPC getInstructor() {
		return getNpcs().closest(this.instructorName);
	}

	/**
	 * Returns the widget for and checks if contains click here to continue
	 * 
	 * @return
	 */
	private RS2Widget getContinueWidget() {
		return getWidgets().singleFilter(getWidgets().getAll(),
				widget -> widget.isVisible() && (widget.getMessage().contains("Click here to continue")
						|| widget.getMessage().contains("Click to continue")));
	}

	/**
	 * Selects to continue the talk
	 * 
	 * @return
	 */
	protected boolean selectContinue() {
		RS2Widget continueWidget = getContinueWidget();
		if (continueWidget == null) {
			return false;
		}
		if (continueWidget.getMessage().contains("Click here to continue")) {
			getKeyboard().pressKey(KeyEvent.VK_SPACE);
			Sleep.sleepUntil(!continueWidget.isVisible(), 1000, 500);
			return true;
		} else if (continueWidget.interact()) {
			Sleep.sleepUntil(!continueWidget.isVisible(), 1000, 500);
			return true;
		}
		return false;
	}

	/**
	 * Is the player in positon?
	 * 
	 * @param position
	 * @return
	 */
	protected boolean isInPosition(Position position) {
		return myPlayer().getPosition().getX() == position.getX() && myPlayer().getPosition().getY() == position.getY()
				&& myPlayer().getPosition().getZ() == position.getZ();
	}

	/**
	 * Talking with the construcot
	 */
	protected void talkToConstructor() {
		if (getInstructor() != null) {
			getInstructor().interact("Talk-to");
			Sleep.sleepUntil(pendingContinue(), 5000, 3000);
		}
	}
	
	/**
	 * 
	 */
	protected void enableRunning() {
		if (getSettings().getRunEnergy() > 50 && !getSettings().isRunning()) {
			getSettings().setRunning(true);
		}
	}

	/**
	 * Waits till the continue widget is visible
	 * 
	 * @return
	 */
	protected boolean pendingContinue() {
		RS2Widget continueWidget = getContinueWidget();
		return continueWidget != null && continueWidget.isVisible();
	}

	/**
	 * @return the instructorName
	 */
	public String getInstructorName() {
		return instructorName;
	}

}
