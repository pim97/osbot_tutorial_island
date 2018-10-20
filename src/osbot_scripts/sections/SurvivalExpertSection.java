package osbot_scripts.sections;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundDecoration;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.event.WalkingEvent;

import osbot_scripts.TestScript;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class SurvivalExpertSection extends TutorialSection {

	public SurvivalExpertSection() {
		super("Survival Expert");
		// TODO Auto-generated constructor stub
	}

	private static final int RAW_SHRIMP = 2514;

	private static final int NORMAL_LOGS = 2511;

	private static final int COOKED_SHRIMP = 315;

	private static final int BURNED_SHRIMP = 7954;

	private static final int FIRE_OBJECT_ID = 26185;

	@Override
	public void onLoop() throws InterruptedException {
		log(getProgress());

		switch (getProgress()) {
		case 20:
			talkAndContinueWithInstructor();
			break;
		case 30:
			talkAndContinueWithInstructor();
			Sleep.sleepUntil(getTabs().open(Tab.INVENTORY), 10000, 1000);
			break;
		case 40:
			chopTree();
			break;
		case 50:
			lightFire();
			break;
		case 60:
			Sleep.sleepUntil(getTabs().open(Tab.SKILLS), 10000, 1000);
			break;
		case 70:
			talkAndContinueWithInstructor();
			break;
		case 80:
			fish();
			break;
		case 90:
		case 110:
			Sleep.sleepUntil(getTabs().open(Tab.INVENTORY), 1000, 1000);

			if (!getInventory().contains(RAW_SHRIMP)) {
				fish();
			} else if (!getInventory().contains(NORMAL_LOGS)) {
				chopTree();
			} else if (getInventory().contains(RAW_SHRIMP, NORMAL_LOGS)) {
				if (!isFireInArea()) {
					lightFire();
				} else {
					useShrimpOnFire();
				}
			} else if (getInventory().contains(BURNED_SHRIMP)) {
				getInventory().dropAll(BURNED_SHRIMP);
			}
			break;
		case 120:
			walkThroughGate();
			break;
			
		case 130:
			TestScript.mainState = getNextMainState();
			break;
		}

	}

	/**
	 * Walking throug the gate
	 */
	private void walkThroughGate() {
		clickObject(9470, "Open", new Position(3091, 3092, 0));
	}

	/**
	 * Fish a fish
	 */
	private void fish() {
		NPC fishingSpot = getNpcs().closest("Fishing spot");
		log("fishspot" + fishingSpot);
		if (fishingSpot != null && fishingSpot.isVisible()) {
			if (fishingSpot.interact("Net")) {
				Sleep.sleepUntil(!myPlayer().isMoving() && myPlayer().getAnimation() == -1, 10000, 5000);
			}
		}
	}

	/**
	 * Chops a tree
	 */
	private void chopTree() {
		RS2Object tree = getObjects().closest(9730);
		if (tree != null && tree.isVisible()) {
			tree.interact("Chop down");

			Sleep.sleepUntil(myPlayer().getAnimation() == -1, 10000, 1000);
		}
	}

	/**
	 * 
	 */
	private void lightFire() {
		if (standingOnFire()) {
			getEmptyPosition().ifPresent(position -> {
				WalkingEvent walkingEvent = new WalkingEvent(position);
				walkingEvent.setMinDistanceThreshold(0);
				execute(walkingEvent);
			});
		} else if (!"Tinderbox".equals(getInventory().getSelectedItemName())) {
			getInventory().getItem("Tinderbox").interact("Use");
		} else if (getInventory().getItem("Logs").interact()) {
			Position playerPos = myPosition();
			Sleep.sleepUntil(!myPosition().equals(playerPos), 10_000, 500);
		}
	}

	private boolean standingOnFire() {
		return getObjects().singleFilter(getObjects().getAll(),
				obj -> obj.getPosition().equals(myPosition()) && obj.getName().equals("Fire")) != null;
	}

	private boolean isFireInArea() {
		List<Position> allPositions = myPlayer().getArea(10).getPositions();

		for (RS2Object object : getObjects().getAll()) {
			if (allPositions.contains(object.getPosition())
					&& object.getId() == FIRE_OBJECT_ID) {
				return true;
			}
		}
		return false;
	}

	private Optional<Position> getEmptyPosition() {
		List<Position> allPositions = myPlayer().getArea(10).getPositions();

		// Remove any position with an object (except ground decorations, as they can be
		// walked on)
		for (RS2Object object : getObjects().getAll()) {
			if (object instanceof GroundDecoration) {
				continue;
			}
			allPositions.removeIf(position -> object.getPosition().equals(position));
		}

		allPositions.removeIf(position -> !getMap().canReach(position));

		return allPositions.stream().min(Comparator.comparingInt(p -> myPosition().distance(p)));
	}

	/**
	 * 
	 */
	private void useShrimpOnFire() {
		Item shrimp = getInventory().getItem("Raw shrimps");
		if (shrimp != null) {
			RS2Object fire = getObjects().closest("Fire");
			if (fire != null && fire.isVisible()) {
				if (shrimp.interact("Use")) {
					if (fire.interact("Use")) {

						Sleep.sleepUntil(myPlayer().getAnimation() == -1, 10000, 5000);
					}
				}
			}
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
		return MainState.COOKING_GUIDE_SECTION;
	}

}
