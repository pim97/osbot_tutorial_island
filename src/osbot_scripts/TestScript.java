package osbot_scripts;

import java.awt.Graphics2D;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.sections.CharacterCreationSection;
import osbot_scripts.sections.CombatGuideSection;
import osbot_scripts.sections.CookingGuideSection;
import osbot_scripts.sections.GuilinorGuideSection;
import osbot_scripts.sections.MiningGuideSection;
import osbot_scripts.sections.QuestGuideSection;
import osbot_scripts.sections.SurvivalExpertSection;
import osbot_scripts.sections.TutorialSection;
import osbot_scripts.sections.decide.CheckInWhatArea;
import osbot_scripts.sections.total.progress.MainState;

@ScriptManifest(author = "test", info = "test", logo = "", name = "test", version = 0)
public class TestScript extends Script {

	/**
	 * The current mainstate
	 */
	public static MainState mainState = MainState.CREATE_CHARACTER_DESIGN;

	/**
	 * Creating a character with random outfit
	 */
	private final TutorialSection characterCreationSection = new CharacterCreationSection();

	/**
	 * Talking to the guilinor guide
	 */
	private final TutorialSection guilinorGuideSection = new GuilinorGuideSection();
	

	/**
	 * Survival Expert section
	 */
	private final TutorialSection survivalExpertSection = new SurvivalExpertSection();
	
	/**
	 * 
	 */
	private final TutorialSection cookingGuideSection = new CookingGuideSection();
	
	/**
	 * 
	 */
	private final TutorialSection questGuideSection = new QuestGuideSection();
	
	/**
	 * 
	 */
	private final TutorialSection miningGuideSection = new MiningGuideSection();
	
	/**
	 * 
	 */
	private final TutorialSection combatGuideSection = new CombatGuideSection();
	

	/**
	 * Loops
	 */
	@Override
	public int onLoop() throws InterruptedException {
		log(mainState);

		if (mainState == MainState.CREATE_CHARACTER_DESIGN) {
			characterCreationSection.onLoop();
		} else if (mainState == MainState.TALK_TO_GIELINOR_GUIDE_ONE) {
			guilinorGuideSection.onLoop();
		} else if (mainState == MainState.SURVIVAL_EXPERT) {
			survivalExpertSection.onLoop();
		} else if (mainState == MainState.COOKING_GUIDE_SECTION) {
			cookingGuideSection.onLoop();
		} else if (mainState == MainState.QUEST_SECTION) {
			questGuideSection.onLoop();
		} else if (mainState == MainState.MINING_SECTION) {
			miningGuideSection.onLoop();
		} else if (mainState == MainState.COMBAT_SECTION) {
			combatGuideSection.onLoop();
		}
		return random(600, 1200);
	}

	@Override
	public void onStart() throws InterruptedException {
		getCharacterCreationSection().exchangeContext(getBot());
		getGuilinorGuideSection().exchangeContext(getBot());
		getSurvivalExpertSection().exchangeContext(getBot());
		getCookingGuideSection().exchangeContext(getBot());
		getQuestGuideSection().exchangeContext(getBot());
		getMiningGuideSection().exchangeContext(getBot());
		getCombatGuideSection().exchangeContext(getBot());
		
		mainState = CheckInWhatArea.getState(this);
		log("Set state to: "+mainState);
		
		// prevents script from skipping character customization
        sleep(4000);
	}

	@Override
	public void onExit() throws InterruptedException {

	}

	@Override
	public void onPaint(Graphics2D g) {

	}

	/**
	 * @return the guilinorGuideSection
	 */
	public TutorialSection getGuilinorGuideSection() {
		return guilinorGuideSection;
	}
	
	/**
	 * 
	 * @return
	 */
	public TutorialSection getCharacterCreationSection() {
		return characterCreationSection;
	}
	

	public TutorialSection getSurvivalExpertSection() {
		return survivalExpertSection;
	}

	/**
	 * @return the cookingGuideSection
	 */
	public TutorialSection getCookingGuideSection() {
		return cookingGuideSection;
	}

	/**
	 * @return the questGuideSection
	 */
	public TutorialSection getQuestGuideSection() {
		return questGuideSection;
	}

	/**
	 * @return the miningGuideSection
	 */
	public TutorialSection getMiningGuideSection() {
		return miningGuideSection;
	}

	/**
	 * @return the combatGuideSection
	 */
	public TutorialSection getCombatGuideSection() {
		return combatGuideSection;
	}

}
