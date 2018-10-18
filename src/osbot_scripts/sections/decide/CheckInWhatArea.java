package osbot_scripts.sections.decide;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.sections.total.progress.MainState;

public class CheckInWhatArea {

	public static MainState getState(MethodProvider prov) {
		if (new Area(new int[][] { { 3095, 9504 }, { 3095, 9493 }, { 3125, 9493 }, { 3122, 9539 }, { 3095, 9536 } })
				.contains(prov.myPlayer())) {
			return MainState.COMBAT_SECTION;
		} else if (new Area(new int[][] { { 3088, 9526 }, { 3082, 9522 }, { 3076, 9517 }, { 3076, 9514 },
				{ 3078, 9511 }, { 3074, 9508 }, { 3071, 9508 }, { 3071, 9493 }, { 3095, 9493 }, { 3095, 9512 },
				{ 3088, 9525 } }).contains(prov.myPlayer())) {
			return MainState.MINING_SECTION;
		} else if (new Area(new int[][] { { 3082, 3126 }, { 3090, 3126 }, { 3090, 3119 }, { 3080, 3119 },
				{ 3080, 3123 }, { 3080, 3124 } }).contains(prov.myPlayer())) {
			return MainState.QUEST_SECTION;
		} else if (new Area(new int[][] { { 3076, 3098 }, { 3082, 3098 }, { 3083, 3099 }, { 3085, 3097 },
				{ 3086, 3097 }, { 3087, 3096 }, { 3088, 3096 }, { 3090, 3094 }, { 3090, 3091 }, { 3089, 3089 },
				{ 3089, 3088 }, { 3090, 3087 }, { 3090, 3084 }, { 3092, 3082 }, { 3092, 3078 }, { 3090, 3077 },
				{ 3090, 3069 }, { 3089, 3069 }, { 3089, 3065 }, { 3090, 3064 }, { 3090, 3056 }, { 3061, 3056 },
				{ 3067, 3082 }, { 3072, 3082 }, { 3073, 3083 }, { 3073, 3087 }, { 3074, 3087 }, { 3074, 3089 },
				{ 3072, 3089 }, { 3072, 3091 }, { 3072, 3092 }, { 3077, 3092 }, { 3077, 3098 } })
						.contains(prov.myPlayer())
				|| new Area(new int[][] { { 3076, 3092 }, { 3072, 3092 }, { 3072, 3082 }, { 3065, 3082 },
						{ 3053, 3096 }, { 3053, 3104 }, { 3063, 3104 }, { 3063, 3116 }, { 3064, 3123 }, { 3069, 3126 },
						{ 3066, 3133 }, { 3068, 3135 }, { 3079, 3135 }, { 3087, 3135 }, { 3093, 3135 }, { 3094, 3129 },
						{ 3095, 3124 }, { 3093, 3119 }, { 3093, 3114 }, { 3088, 3114 }, { 3083, 3106 }, { 3083, 3098 },
						{ 3076, 3098 } }).contains(prov.myPlayer())) {
			return MainState.COOKING_GUIDE_SECTION;
		} else if (new Area(new int[][] { { 3087, 3102 }, { 3096, 3102 }, { 3096, 3105 }, { 3098, 3106 },
				{ 3098, 3110 }, { 3096, 3111 }, { 3096, 3113 }, { 3092, 3113 }, { 3092, 3111 }, { 3090, 3111 },
				{ 3090, 3108 }, { 3087, 3108 } }).contains(prov.myPlayer())) {
			return MainState.CREATE_CHARACTER_DESIGN;
		} else if (new Area(new int[][] { { 3098, 3112 }, { 3102, 3112 }, { 3104, 3107 }, { 3107, 3100 },
				{ 3107, 3091 }, { 3102, 3086 }, { 3098, 3086 } }).contains(prov.myPlayer())
				|| new Area(new int[][] { { 3096, 3099 }, { 3083, 3099 }, { 3086, 3096 }, { 3090, 3096 },
						{ 3090, 3090 }, { 3092, 3089 }, { 3095, 3089 }, { 3097, 3087 }, { 3102, 3087 }, { 3105, 3089 },
						{ 3107, 3093 }, { 3108, 3099 } }).contains(prov.myPlayer())) {
			return MainState.SURVIVAL_EXPERT;
		}
		return null;
	}
}
