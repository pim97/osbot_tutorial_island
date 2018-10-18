package osbot_scripts.sections;

import java.util.Random;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TestScript;
import osbot_scripts.sections.progress.CookingGuideSectionProgress;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class CookingGuideSection extends TutorialSection {

	CookingGuideSectionProgress progress = CookingGuideSectionProgress.OPEN_DOOR;

	public CookingGuideSection() {
		super("Master Chef");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLoop() throws InterruptedException {
		log(progress);

		if (getWidgets().containingText("For those situations where words don't quite") != null) {
			progress = CookingGuideSectionProgress.WALK_TO_DUNGEON;
		} else if (getWidgets().containingText("Now, how about showing some feelings?") != null) {
			progress = CookingGuideSectionProgress.CLICKING_EMOTES;
		} else if (getWidgets().containingText("From this interface you can control the music") != null) {
			progress = CookingGuideSectionProgress.CLICKING_DOOR_TO_OUTSIDE;
		} else if (getWidgets().containingText("Well done! Your first loaf of bread") != null) {
			progress = CookingGuideSectionProgress.CLICKING_MUSIC_PLAYER;
		} else if (getWidgets().containingText("Now you have made dough") != null) {
			progress = CookingGuideSectionProgress.DOUGH_ON_RANGE;
		} else if (getWidgets().containingText("This is the base for many") != null) {
			progress = CookingGuideSectionProgress.MAKING_DOUGH;
		} else if (getWidgets().containingText("Talk to the chef indicated") != null) {
			progress = CookingGuideSectionProgress.TALK_TO_COOKING_GUIDE;
		} else if (getWidgets().containingText("Click on the door to open it. Notice the mini-map") != null) {
			progress = CookingGuideSectionProgress.OPEN_DOOR;
		}

		if (progress == CookingGuideSectionProgress.OPEN_DOOR) {
			RS2Object doorObject = getObjects().closest(9709);

			if (doorObject != null && doorObject.isVisible()) {
				if (doorObject.interact("Open")) {
					progress = CookingGuideSectionProgress.TALK_TO_COOKING_GUIDE;
					Sleep.sleepUntil(new Area(new int[][] { { 3078, 3089 }, { 3075, 3089 }, { 3075, 3086 },
							{ 3079, 3086 }, { 3079, 3089 } }).contains(myPlayer().getPosition()), 10000, 5000);
				}
			} else if (doorObject != null && !doorObject.isVisible()) {
				getWalking().walk(doorObject.getPosition());
			}
		} else if (progress == CookingGuideSectionProgress.TALK_TO_COOKING_GUIDE) {
			if (!pendingContinue()) {
				talkToConstructor();
				// Select to continue if can continue
			} else if (pendingContinue()) {
				selectContinue();
			}
		} else if (progress == CookingGuideSectionProgress.MAKING_DOUGH) {
			Item flour = getInventory().getItem(2516);
			if (flour != null) {
				Item bucketOfWater = getInventory().getItem(1929);

				if (bucketOfWater != null) {
					bucketOfWater.interact("Use");
					flour.interact("Use");
					Sleep.sleepUntil(getInventory().contains(2307), 2000, 1000);
					progress = CookingGuideSectionProgress.DOUGH_ON_RANGE;
				}
			}
		} else if (progress == CookingGuideSectionProgress.DOUGH_ON_RANGE) {
			RS2Object fireRange = getObjects().closest(9736);
			if (fireRange != null && fireRange.isVisible()) {
				fireRange.interact("Cook");
				Sleep.sleepUntil(getInventory().contains(2309), 4000, 1000);
				progress = CookingGuideSectionProgress.CLICKING_MUSIC_PLAYER;
			} else if (fireRange != null && !fireRange.isVisible()) {
				getWalking().walk(fireRange.getPosition());
			}
		} else if (progress == CookingGuideSectionProgress.CLICKING_MUSIC_PLAYER) {
			if (getTabs().open(Tab.MUSIC)) {
				progress = CookingGuideSectionProgress.CLICKING_DOOR_TO_OUTSIDE;
			}
		} else if (progress == CookingGuideSectionProgress.CLICKING_DOOR_TO_OUTSIDE) {
			clickObject(9710, "Open");
		} else if (progress == CookingGuideSectionProgress.CLICKING_EMOTES) {
			if (getTabs().open(Tab.EMOTES)) {
				RS2Widget emoteWidget = getWidgets().get(216, 1, new Random().nextInt(20));
				if (emoteWidget != null) {
					emoteWidget.interact();
					Sleep.sleepUntil(!myPlayer().isAnimating(), 3000, 1000);
					if (getTabs().open(Tab.SETTINGS)) {
						if (!getSettings().isRunning()) {
							getSettings().setRunning(true);
							Sleep.sleepUntil(getSettings().isRunning(), 3000, 1000);
							progress = CookingGuideSectionProgress.WALK_TO_DUNGEON;
						}
					}
				}
			}
		} else if (progress == CookingGuideSectionProgress.WALK_TO_DUNGEON) {
			Position doorPosition = new Position(3085, 3127, 0);
			getWalking().walk(doorPosition);
			Sleep.sleepUntil(myPlayer().getArea(5).contains(doorPosition), 10000, 3000);
			
			clickObject(9716, "Open");

			Sleep.sleepUntil(new Area(new int[][] { { 3082, 3126 }, { 3090, 3126 }, { 3090, 3119 }, { 3080, 3119 },
					{ 3080, 3123 }, { 3080, 3124 } }).contains(myPlayer().getPosition()), 10000, 3000);
			
			TestScript.mainState = getNextMainState();
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
		return MainState.QUEST_SECTION;
	}

}
