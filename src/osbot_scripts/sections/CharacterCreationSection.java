package osbot_scripts.sections;

import java.util.Random;

import org.osbot.rs07.api.ui.RS2Widget;

import osbot_scripts.TutorialScript;
import osbot_scripts.sections.progress.CharacterCreationSectionProgress;
import osbot_scripts.sections.total.progress.MainState;

public class CharacterCreationSection extends TutorialSection {
	
	/**
	 * Current progress
	 */
	public CharacterCreationSectionProgress progress = CharacterCreationSectionProgress.CREATING_RANDOM_CHARACTER;

	/**
	 * Parent ID for the inferface when creating a character
	 */
	public static final int DESIGN_CHARACTER_PARENT_ID = 269;
	
	/**
	 * Time between selecting a specific part of a character
	 */
	public static final int TIME_BETWEEN_DESIGN_CHARACTER = 2000;
	
	/**
	 * Constructor
	 * @param mainState
	 */
	public CharacterCreationSection() {
		super(null);
	}

	/**
	 * Loop
	 */
	@Override
    public final void onLoop() throws InterruptedException {
		if (!isCompleted() && getProgress() <= 0) {
			createCharacterDesign();
			log("Not yet completed");
		} else {
			TutorialScript.mainState = getNextMainState();
			log("Section completed!");
		}
	}

	/**
	 * Returns the next state
	 */
	@Override
	public MainState getNextMainState() {
		// TODO Auto-generated method stub
		return MainState.TALK_TO_GIELINOR_GUIDE_ONE;
	}
	
	/**
	 * Is the section completed?
	 */
	@Override
	public boolean isCompleted() {
		return getProgress() == 0 && getWidgets().get(269, 96) == null ? true : false;
	}
	
	/**
	 * Creates an unique character design
	 * 
	 * @throws InterruptedException
	 */
	private void createCharacterDesign() throws InterruptedException {
		if (progress == CharacterCreationSectionProgress.CREATING_RANDOM_CHARACTER) {
			progress = CharacterCreationSectionProgress.CHOOSING_HEAD;
		} else if (progress == CharacterCreationSectionProgress.CHOOSING_HEAD) {
			RS2Widget designCharacterWidgetHead = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 113);
			if (designCharacterWidgetHead != null && designCharacterWidgetHead.isVisible()) {
				Random rand = new Random();
				int random = rand.nextInt(3) + 1;
				for (int i = 0; i < random; i++) {
					sleep(rand.nextInt(TIME_BETWEEN_DESIGN_CHARACTER));
					designCharacterWidgetHead.interact("Change head");
				}
			}
			progress = CharacterCreationSectionProgress.CHOOSING_JAW;
		} else if (progress == CharacterCreationSectionProgress.CHOOSING_JAW) {
			RS2Widget designCharacterWidgetJaw = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 114);
			if (designCharacterWidgetJaw != null && designCharacterWidgetJaw.isVisible()) {
				Random rand = new Random();
				int random = rand.nextInt(3) + 1;
				for (int i = 0; i < random; i++) {
					sleep(rand.nextInt(TIME_BETWEEN_DESIGN_CHARACTER));
					designCharacterWidgetJaw.interact("Change jaw");
				}
			}
			progress = CharacterCreationSectionProgress.CHOOSING_TORSO;
		} else if (progress == CharacterCreationSectionProgress.CHOOSING_TORSO) {
			RS2Widget designCharacterWidgetTorso = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 115);
			if (designCharacterWidgetTorso != null && designCharacterWidgetTorso.isVisible()) {
				Random rand = new Random();
				int random = rand.nextInt(3) + 1;
				for (int i = 0; i < random; i++) {
					sleep(rand.nextInt(TIME_BETWEEN_DESIGN_CHARACTER));
					designCharacterWidgetTorso.interact("Change torso");
				}
			}
			progress = CharacterCreationSectionProgress.CHOOSING_ARMS;
		} else if (progress == CharacterCreationSectionProgress.CHOOSING_ARMS) {
			RS2Widget designCharacterWidgetArms = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 116);
			if (designCharacterWidgetArms != null && designCharacterWidgetArms.isVisible()) {
				Random rand = new Random();
				int random = rand.nextInt(3) + 1;
				for (int i = 0; i < random; i++) {
					sleep(rand.nextInt(TIME_BETWEEN_DESIGN_CHARACTER));
					designCharacterWidgetArms.interact("Change arms");
				}
			}
			progress = CharacterCreationSectionProgress.CHOOSING_HANDS;
		} else if (progress == CharacterCreationSectionProgress.CHOOSING_HANDS) {
			RS2Widget designCharacterWidgetHands = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 117);
			if (designCharacterWidgetHands != null && designCharacterWidgetHands.isVisible()) {
				Random rand = new Random();
				int random = rand.nextInt(3) + 1;
				for (int i = 0; i < random; i++) {
					sleep(rand.nextInt(TIME_BETWEEN_DESIGN_CHARACTER));
					designCharacterWidgetHands.interact("Change hands");
				}
			}
			progress = CharacterCreationSectionProgress.CHOOSING_LEGS;
		} else if (progress == CharacterCreationSectionProgress.CHOOSING_LEGS) {
			RS2Widget designCharacterWidgetLegs = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 118);
			if (designCharacterWidgetLegs != null && designCharacterWidgetLegs.isVisible()) {
				Random rand = new Random();
				int random = rand.nextInt(3) + 1;
				for (int i = 0; i < random; i++) {
					sleep(rand.nextInt(TIME_BETWEEN_DESIGN_CHARACTER));
					designCharacterWidgetLegs.interact("Change legs");
				}
			}
			progress = CharacterCreationSectionProgress.CHOOSING_FEET;
		} else if (progress == CharacterCreationSectionProgress.CHOOSING_FEET) {
			RS2Widget designCharacterWidgetFeet = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 119);
			if (designCharacterWidgetFeet != null && designCharacterWidgetFeet.isVisible()) {
				Random rand = new Random();
				int random = rand.nextInt(3) + 1;
				for (int i = 0; i < random; i++) {
					sleep(rand.nextInt(TIME_BETWEEN_DESIGN_CHARACTER));
					designCharacterWidgetFeet.interact("Change feet");
				}
			}
			progress = CharacterCreationSectionProgress.ACCEPT_CHARACTER;
		} else if (progress == CharacterCreationSectionProgress.ACCEPT_CHARACTER) {
			RS2Widget widgetAcceptCharacter = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 100);
			if (widgetAcceptCharacter.interact("Accept")) {
				progress = CharacterCreationSectionProgress.TALK_WITH_NPC_1;
			} else {
				progress = CharacterCreationSectionProgress.CREATING_RANDOM_CHARACTER;
			}
		}

	}
}
