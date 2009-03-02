package com.planet_ink.coffee_mud.Abilities.Languages;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.util.*;

/* 
   Copyright 2000-2009 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

@SuppressWarnings("unchecked")
public class Fey extends StdLanguage
{
	public String ID() { return "Fey"; }//Based on Gevey from http://www.kalieda.org/gevey/lexicon.html
	public String name(){ return "Fey";}
	public static Vector wordLists=null;	
	private static boolean mapped=false;
	public Fey()
	{
		super();
		if(!mapped){mapped=true;
					CMLib.ableMapper().addCharAbilityMapping("All",1,ID(),false);}
	}
	public CMObject newInstance()
    {	
        return new Fey();
    }
	public Vector translationVector(String language)
	{ 
		if(wordLists==null)
		{
			String[] one={"se","rh","al","o","na","e","ce","uu","si","lag"};
			String[] two={"ta","Sha","rha","den","oc","han","mij","ma","ke","isc","ga","en","di","de","an","brois","ajet","ev","ba","eh","bas"};
			String[] three={"vav","set","sav","sen","rhig","viy","ok","nash","mik","kiy","hask","tal","gha","dos","diy","ceg","dak","aeln","huu","beksi","bat","bal","aghblajliyookuu","agluef"};
			String[] four={"tath","pehm","nith","nana","jaath","isc","glou","gafr","dost","daes","ciel","cahve","begm","braup","blom","belag","abrol","adeci","agehr","ajet","basacont"};
			String[] five={"shasy","rewist","pae","nuwan","noest","nafozdomuu","kreet","jaas","guhnt","glaa","caebiste","abdist","afosk","belguu","andenav","asatehrh"};
			String[] six={"sats","retas","pokat","os","mieplust-eh","iha","hosh","griste","fot","begmevrhe","abdisem","soufuuwh","aelnebaduuu"};
			wordLists=new Vector();
			wordLists.addElement(one);
			wordLists.addElement(two);
			wordLists.addElement(three);
			wordLists.addElement(four);
			wordLists.addElement(five);
			wordLists.addElement(six);
		}
		return wordLists; 
	}
	private static final Hashtable hashwords=new Hashtable();
	public Hashtable translationHash(String language)
	{
		if((hashwords!=null)&&(hashwords.size()>0)) 
			return hashwords;

		hashwords.put("ABANDON","fnuuthan");
		hashwords.put("ABOVE","vosy");
		hashwords.put("ACCEPTABLE","wilr");
		hashwords.put("ACROSS","mava");
		hashwords.put("ACTION","baluu");
		hashwords.put("ADD","segafran");
		hashwords.put("ADDITIONAL","gafr");
		hashwords.put("ADMIRE","abdismuu");
		hashwords.put("ADMIRER","abdiste");
		hashwords.put("ADULT","cuklame");
		hashwords.put("ADULTHOOD","cuklamconuu");
		hashwords.put("ADVENTURE","noi'holhguu");
		hashwords.put("AFTERNOON","viejluu");
		hashwords.put("AGAIN","gant-eh");
		hashwords.put("AGE","sekaskrhandomuu");
		hashwords.put("AGGRESSIVE","mahc");
		hashwords.put("AGREE","noiraznan");
		hashwords.put("AGREEMENT","razent");
		hashwords.put("AIR","khaasluu");
		hashwords.put("AIRSHIP","shunoguu");
		hashwords.put("ALL","laes");
		hashwords.put("ALONE","pout");
		hashwords.put("ALONG","basy");
		hashwords.put("ALPHABET","beecuu");
		hashwords.put("ALTHOUGH","cuhset");
		hashwords.put("AM","en");
		hashwords.put("AMAZING","oomuhfeshp");
		hashwords.put("AMAZINGLY","oomuhfeshp-eh");
		hashwords.put("AMUSE","sneklan");
		hashwords.put("AMUSEMENT","sneklizmuu");
		hashwords.put("ANCIENT","askrhan");
		hashwords.put("AND","he");
		hashwords.put("ANGER","zhgishte");
		hashwords.put("ANGRILY","moghl-eh");
		hashwords.put("ANGRY","moghl");
		hashwords.put("ANIMAL","akhmar");
		hashwords.put("ANIMALS","jozge");
		hashwords.put("ANKLE","vaguu");
		hashwords.put("ANSWER","straacohpuu");
		hashwords.put("ANT","seze");
		hashwords.put("ANTHILL","sezroubuu");
		hashwords.put("APARTMENT","jesmitaluu");
		hashwords.put("APARTMENT","oekefuu");
		hashwords.put("APPEAR","rintsan");
		hashwords.put("ARE","en");
		hashwords.put("AREA","lhikuu");
		hashwords.put("ARENA","goeksnekluu");
		hashwords.put("ARGUE","zavben");
		hashwords.put("ARM","vroiduu");
		hashwords.put("AROUND","celka");
		hashwords.put("ARSENAL","mokhovuu");
		hashwords.put("ASK","dhoetrhan");
		hashwords.put("ASSERTIVE","ahhid");
		hashwords.put("ASSISTANCE","akothuu");
		hashwords.put("ASTONISHING","oomuhfeshp");
		hashwords.put("ASTONISHINGLY","oomuhfeshp-eh");
		hashwords.put("ASTONISHMENT","oomuhfuu");
		hashwords.put("ATTENDANT","pundiste");
		hashwords.put("AUNT","agatafe");
		hashwords.put("AVALANCHE","blajliswujuu");
		hashwords.put("AWE","oomuhfuu");
		hashwords.put("BACK","glawuu");
		hashwords.put("BAD","dos");
		hashwords.put("BAKER","begmevehr");
		hashwords.put("BAKER","panevrhe");
		hashwords.put("BAKERY","begmov");
		hashwords.put("BAKERY","panagzuu");
		hashwords.put("BALL","laebuu");
		hashwords.put("BANANA","dhijuu");
		hashwords.put("BAND","nounshove");
		hashwords.put("BANDIT","loiflaeye");
		hashwords.put("BAR","goekceguu");
		hashwords.put("BARRICADE","noidoeftan");
		hashwords.put("BASIN","oshantuu");
		hashwords.put("BASKET","caatuu");
		hashwords.put("BATHROOM","goekkhoivuu");
		hashwords.put("BE","en");
		hashwords.put("BEAST","jozge");
		hashwords.put("BEAUTIFULLY","stoen-eh");
		hashwords.put("BECOME","sekan");
		hashwords.put("BED","gribdaluu");
		hashwords.put("BEDROOM","goektaluu");
		hashwords.put("BEFORE","ant");
		hashwords.put("BEFREIDN","butsohslan");
		hashwords.put("BEG","tuktlen");
		hashwords.put("BEHIND","deby");
		hashwords.put("BELLOW","zavben");
		hashwords.put("BELLY","osucuu");
		hashwords.put("BELOW","suus");
		hashwords.put("BETWEEN","dezde");
		hashwords.put("BIG","jarh");
		hashwords.put("BIRD","thoele");
		hashwords.put("BIRTH","butsnoeman");
		hashwords.put("BITE","trhalhan");
		hashwords.put("BLACK","sal");
		hashwords.put("BLIMP","shunoguu");
		hashwords.put("BLOCK","noidoeftan");
		hashwords.put("BLUE","oun");
		hashwords.put("BOAT","osemuu");
		hashwords.put("BOATMAN","osemevrhe");
		hashwords.put("BODY","ces");
		hashwords.put("BOIL","ezezhman");
		hashwords.put("BONE","koguu");
		hashwords.put("BOOK","shubuu");
		hashwords.put("BOTTLE","yapthuu");
		hashwords.put("BOWL","oshantuu");
		hashwords.put("BOX","glomuu");
		hashwords.put("BOXED","zdiglom");
		hashwords.put("BOY","rapte");
		hashwords.put("BRANCH","pouzluu");
		hashwords.put("BRAWL","bwoefohp");
		hashwords.put("BREAD","panuu");
		hashwords.put("BREAK","shaablan");
		hashwords.put("BREED","budzgoegan");
		hashwords.put("BREEDER","budzgoeganuu");
		hashwords.put("BREW","gadran");
		hashwords.put("BRICK","jethuu");
		hashwords.put("BRIDGE","vulmuu");
		hashwords.put("BRIGHT","bludr");
		hashwords.put("BRING","riethan");
		hashwords.put("BROAD","foeddh");
		hashwords.put("BROTHEL","sharhavuu");
		hashwords.put("BROTHER","huspe");
		hashwords.put("BROWN","zaec");
		hashwords.put("BUCKET","wuujuu");
		hashwords.put("BUFFALO","oekuefse");
		hashwords.put("BUILD","butsan");
		hashwords.put("BUILDS","nezhlan");
		hashwords.put("BURN","rofan");
		hashwords.put("BUT","ap");
		hashwords.put("BUY","tathan");
		hashwords.put("BY","ba");
		hashwords.put("CABBAGE","fuhstuu");
		hashwords.put("CAFE","spaghavuu");
		hashwords.put("CALLED","stosh");
		hashwords.put("CALM","juul");
		hashwords.put("CAN","brav");
		hashwords.put("CANDLE","shuafuu");
		hashwords.put("CANTEEN","goekfosuu");
		hashwords.put("CAPTIVE","kaptakel");
		hashwords.put("CAPTURE","akhkapet");
		hashwords.put("CAPTURE","kaptan");
		hashwords.put("CAR","poe'hajuu");
		hashwords.put("CAREFUL","grav");
		hashwords.put("CAREFULLY","grav-eh");
		hashwords.put("CARVE","muefan");
		hashwords.put("CAT","luetse");
		hashwords.put("CB","ojoikluu");
		hashwords.put("CEGMUU","shoe");
		hashwords.put("CELEBRATE","kezguzmen");
		hashwords.put("CHAIR","fresluu");
		hashwords.put("CHANGE","dhoshopen");
		hashwords.put("CHAOTIC","bab");
		hashwords.put("CHASE","rispan");
		hashwords.put("CHAT","kegen");
		hashwords.put("CHEESE","lizhvuu");
		hashwords.put("CHEST","storuu");
		hashwords.put("CHICKEN","oh'heele");
		hashwords.put("CHILD","basat");
		hashwords.put("CHILD","dhoire");
		hashwords.put("CHISEL","muefan");
		hashwords.put("CHOICE","trugoisuu");
		hashwords.put("CHOOSE","trugan");
		hashwords.put("CHOP","shaablan");
		hashwords.put("CHORE","akhuebnisk");
		hashwords.put("CHRISTEN","sekstoshan");
		hashwords.put("CITRUS","zerajuu");
		hashwords.put("CITY","vopshe");
		hashwords.put("CLAN","galn'hiete");
		hashwords.put("CLAY","luzhnuu");
		hashwords.put("CLEAR","tsaman");
		hashwords.put("CLIMB","mispen");
		hashwords.put("CLINIC","jaarveefuu");
		hashwords.put("CLOAK","lufluu");
		hashwords.put("CLOAK","noiluflan");
		hashwords.put("CLOTHES","dhoftuu");
		hashwords.put("CLOTHING","dondomuu");
		hashwords.put("CLOUD","drhelhuu");
		hashwords.put("CLOVER","noiluflan");
		hashwords.put("CLUB","goekceguu");
		hashwords.put("COBBLER","cegmathe");
		hashwords.put("COBBLING","cegm");
		hashwords.put("COCKEREL","oh'heele");
		hashwords.put("COFFEE","spaghuu");
		hashwords.put("COLD","skug");
		hashwords.put("COLLEGE","krasovuu");
		hashwords.put("COMMENCE","boh");
		hashwords.put("COMMUNICATOR","threezuu");
		hashwords.put("COMPARE","bas");
		hashwords.put("COMPLETE","suatan");
		hashwords.put("COMPOSE","butsnounshan");
		hashwords.put("COMPOSITION","butsnounshanuu");
		hashwords.put("COMPUTER","gaurhtuu");
		hashwords.put("CONFUSE","noibaban");
		hashwords.put("CONFUSION","noibabuu");
		hashwords.put("CONSTRUCT","nezhlan");
		hashwords.put("CONTINENT","shkuubuu");
		hashwords.put("CONTINUOUS","les");
		hashwords.put("CONTRACT","raznuu");
		hashwords.put("CONTROL","tuuznan");
		hashwords.put("COOK","noibegman");
		hashwords.put("CORKBOARD","doeftkhavyuu");
		hashwords.put("CORN","sastrhuu");
		hashwords.put("COUGH","lekuu");
		hashwords.put("COUNT","jekan");
		hashwords.put("COUNTING","okijekizmuu");
		hashwords.put("COURTROOM","soghovuu");
		hashwords.put("COURTYARD","bodmuu");
		hashwords.put("COUSIN","rhaaje");
		hashwords.put("CRAB","stuuwhe");
		hashwords.put("CREATION","nezhlakluu");
		hashwords.put("CRY","wihdren");
		hashwords.put("SUN","wage");
		hashwords.put("CUP","ladhuu");
		hashwords.put("DAD","bizhev");
		hashwords.put("DATA","ghawuu");
		hashwords.put("DAUGHTER","joose");
		hashwords.put("DAY","resuu");
		hashwords.put("DAYCARE","basatag");
		hashwords.put("DAYLIGHT","jevuu");
		hashwords.put("DECOMPOSE","thuzden");
		hashwords.put("DEEP","yaez");
		hashwords.put("DELIVER","riethan");
		hashwords.put("DELTA","dhoun'hietuu");
		hashwords.put("DEMON","leshpe");
		hashwords.put("DEPARTURE","agruu");
		hashwords.put("DERIGIBLE","shunoguu");
		hashwords.put("DESPITE","grhaetap");
		hashwords.put("DESERT","thraasan");
		hashwords.put("DESTROY","kezan");
		hashwords.put("DETERMINATION","emuhstruu");
		hashwords.put("DETERMINED","emuhstralb");
		hashwords.put("DIFFERENT","fot");
		hashwords.put("DIRT","flaeyuu");
		hashwords.put("DISASTER","grhuhkuu");
		hashwords.put("DISEASE","oshaathuu");
		hashwords.put("DISEASED","jaarvan");
		hashwords.put("DISTANCE","nuubruu");
		hashwords.put("DO","gven");
		hashwords.put("DOCTOR","picatehrh");
		hashwords.put("DOES","noi'han");
		hashwords.put("DOG","tuusrhe");
		hashwords.put("DOMESTICAED","maw");
		hashwords.put("DOMESTICATE","mawam");
		hashwords.put("DOOMSAYER","jaarviste");
		hashwords.put("DOOR","tuerhuu");
		hashwords.put("DOUGH","panetuu");
		hashwords.put("DOWN","modo");
		hashwords.put("DRAW","cublan");
		hashwords.put("DRINK","cegan");
		hashwords.put("DRINKS","luedh");
		hashwords.put("DRIVE","zetsan");
		hashwords.put("DRIVER","zetsiste");
		hashwords.put("DROP","blajlan");
		hashwords.put("DRY","altrh");
		hashwords.put("DURING","vih");
		hashwords.put("DUSK","tincuu");
		hashwords.put("EACH","brants");
		hashwords.put("EAR","aelne");
		hashwords.put("EAT","fosan");
		hashwords.put("EFFECT","miepluu");
		hashwords.put("EFFICIENT","dvojust");
		hashwords.put("EFFICIENTLY","dvojust-eh");
		hashwords.put("EIGHT","espenu");
		hashwords.put("ELBOW","vaguu");
		hashwords.put("ELECTRIC","thecshp");
		hashwords.put("ELECTRICITY","thecye");
		hashwords.put("ELSEWHERE","kuuntsau");
		hashwords.put("EMBER","thrumuu");
		hashwords.put("EMOTION","meghe");
		hashwords.put("EMPTY","prozd");
		hashwords.put("END","suatismuu");
		hashwords.put("ENGINEER","batath");
		hashwords.put("ENOUGH","jarh");
		hashwords.put("ENJOY","sekraajan");
		hashwords.put("ENJOYMENT","traraaje");
		hashwords.put("ENTER","puuzen");
		hashwords.put("ENTERTAINER","sneklinde");
		hashwords.put("EQUALLY","blodrh-eh");
		hashwords.put("EVENING","salhkuu");
		hashwords.put("EVENT","gouthrhuu");
		hashwords.put("EVERY","brants");
		hashwords.put("EXCHANGE","rheegrhan");
		hashwords.put("EXCLUDE","khipan");
		hashwords.put("EXPERIENCE","noishloektan");
		hashwords.put("EXTRA","gafr");
		hashwords.put("EYE","wanhe");
		hashwords.put("EYESIGHT","agluefuu");
		hashwords.put("FACTORY","nezhleefuu");
		hashwords.put("FAIL","khuulman");
		hashwords.put("FAILING","khuulmizmuu");
		hashwords.put("FAILURE","khuulmohpuu");
		hashwords.put("FALL","blajlen");
		hashwords.put("FAMILY","cahve");
		hashwords.put("FARM","luutrhan");
		hashwords.put("FARMING","thraejwan");
		hashwords.put("FAST","vit");
		hashwords.put("FAT","zdej");
		hashwords.put("FATHER","bizhve");
		hashwords.put("FEAST","guzmuu");
		hashwords.put("FEATHER","ujetsuu");
		hashwords.put("FEED","whohnan");
		hashwords.put("FEW","suuft");
		hashwords.put("FEWER","suuft");
		hashwords.put("FIELD","deefsuu");
		hashwords.put("FIGTH","bwoefan");
		hashwords.put("FILL","shopcan");
		hashwords.put("FIND","frudhan");
		hashwords.put("FINDING","frudhdomuu");
		hashwords.put("FINGER","bazhev");
		hashwords.put("FINISH","suatismuu");
		hashwords.put("FIRE","heptalte");
		hashwords.put("FIREARM","mokhuu");
		hashwords.put("FIREWOOD","pouzlaelhuu");
		hashwords.put("FIVE","finu");
		hashwords.put("FLAME","hepte");
		hashwords.put("FLESH","shievuu");
		hashwords.put("FLINT","strotuu");
		hashwords.put("FLOOR","gripnuu");
		hashwords.put("FLOUR","sastrhaelhuu");
		hashwords.put("FLOWER","hosh");
		hashwords.put("FLY","ta'shunen");
		hashwords.put("FOLD","akhlathel");
		hashwords.put("FOLLISHLY","nivw-eh");
		hashwords.put("FOOD","afosuu");
		hashwords.put("FOOL","tranivwe");
		hashwords.put("FOOLISH","nivw");
		hashwords.put("FOOT","ezekuu");
		hashwords.put("FOR","ist");
		hashwords.put("FOREHEAD","drestuu");
		hashwords.put("FOREST","rhabgietuu");
		hashwords.put("FORK","zobuu");
		hashwords.put("FORTY","vilhki");
		hashwords.put("FOUR","vilu");
		hashwords.put("FREEZE","griban");
		hashwords.put("FRIEND","ohsle");
		hashwords.put("FRIENDLY","ohslust");
		hashwords.put("FRIENDSHIP","ohsle'hiete");
		hashwords.put("FROM","dost");
		hashwords.put("FRONT","cisa");
		hashwords.put("FROTH","shaesen");
		hashwords.put("FRUIT","brayuu");
		hashwords.put("FUEL","prinyuu");
		hashwords.put("FUN","tratejre");
		hashwords.put("FUNCTION","gouthrhuu");
		hashwords.put("FUNNY","tejr");
		hashwords.put("FUTURE","tragistruu");
		hashwords.put("GALLEY","goegwiekfuu");
		hashwords.put("GARDEN","andent");
		hashwords.put("GARDENER","andenevrhe");
		hashwords.put("GENERALLY","as");
		hashwords.put("GIANT","loifuhrhe");
		hashwords.put("GIFT","zguprhuu");
		hashwords.put("GIRL","vuefne");
		hashwords.put("GIVE","priman");
		hashwords.put("GLASS","strivuu");
		hashwords.put("GLOBE","laebuu");
		hashwords.put("GLOVE","prhihduu");
		hashwords.put("GO","gren");
		hashwords.put("GOAT","rhahshke");
		hashwords.put("GOD","jaakra");
		hashwords.put("GOLD","liguu");
		hashwords.put("GOOD","cul");
		hashwords.put("GOODNESS","traculuu");
		hashwords.put("GOSSIP","doscismuu");
		hashwords.put("GOUGE","muefan");
		hashwords.put("GRAIN","sastrhuu");
		hashwords.put("GRANDFATHER","bizhvete");
		hashwords.put("GRASS","brituu");
		hashwords.put("GRATITUDE","woimuu");
		hashwords.put("GRATZ","dak");
		hashwords.put("GRAY","spoj");
		hashwords.put("GREEDY","laejwiv");
		hashwords.put("GREEN","fuubl");
		hashwords.put("GREETINGS","haetahzu");
		hashwords.put("GREY","spoj");
		hashwords.put("GROUP","bustan");
		hashwords.put("GROW","butsnumpan");
		hashwords.put("GUIDE","zetsiste");
		hashwords.put("GUILD","galn'hiete");
		hashwords.put("GUN","mokhuu");
		hashwords.put("GURU","soghathrhe");
		hashwords.put("HALL","goe'gaeshuu");
		hashwords.put("HALT","jetan");
		hashwords.put("HAMLET","shlepietuu");
		hashwords.put("HAMMER","zwuunhuu");
		hashwords.put("HAND","shaawe");
		hashwords.put("HARBOR","aruask");
		hashwords.put("HARDEN","segwithan");
		hashwords.put("HATCH","bwoeshen");
		hashwords.put("HAVE","ben");
		hashwords.put("HE","eske");
		hashwords.put("HEAD","slofre");
		hashwords.put("HEALER","picatrhe");
		hashwords.put("HEALTH","traswoenuu");
		hashwords.put("HEAR","dhevan");
		hashwords.put("HEAVILY","ouc-eh");
		hashwords.put("HEAVY","ouc");
		hashwords.put("HELLO","haetu");
		hashwords.put("HELP","akoth");
		hashwords.put("HELPFUL","kodhjasp");
		hashwords.put("HELPFULLY","kodhjasp-eh");
		hashwords.put("HEN","oh'heele");
		hashwords.put("HER","afkiy");
		hashwords.put("HERDER","mawatrhe");
		hashwords.put("HERE","delau");
		hashwords.put("HIGH","koun");
		hashwords.put("HILL","thakluu");
		hashwords.put("HILLS","thakel");
		hashwords.put("HIM","eskiy");
		hashwords.put("HIP","vaguu");
		hashwords.put("HIS","esken");
		hashwords.put("HIT","guucan");
		hashwords.put("HOLIDY","daesyuu");
		hashwords.put("HOLY","daes");
		hashwords.put("HOOF","ezekuu");
		hashwords.put("HORSE","krhovle");
		hashwords.put("HOSPITAL","jaavagzuu");
		hashwords.put("HOT","mohsh");
		hashwords.put("HOUR","feznuu");
		hashwords.put("HOUSE","roubuu");
		hashwords.put("HOW","keeb");
		hashwords.put("HUNGER","astragh");
		hashwords.put("HUNT","fnagan");
		hashwords.put("HURT","glikan");
		hashwords.put("HUSBAND","buukrhe");
		hashwords.put("I","ce");
		hashwords.put("ICE","gribohpuu");
		hashwords.put("IDIOT","tranivwe");
		hashwords.put("ILL","theen");
		hashwords.put("ILLNESS","oshaathuu");
		hashwords.put("IMMEDIATE","nasl");
		hashwords.put("IMMEDIATELY","nasl-eh");
		hashwords.put("IMPORTANT","bej");
		hashwords.put("IMPORTANTLY","bej-eh");
		hashwords.put("IN","ist");
		hashwords.put("INBETWEEN","dezd");
		hashwords.put("INFANT","dhoir");
		hashwords.put("INFO","ghawuu");
		hashwords.put("INFORMATION","ghawuu");
		hashwords.put("INSTEAD","ikr-eh");
		hashwords.put("INTO","isc");
		hashwords.put("IS","en");
		hashwords.put("ISLAND","yaabluu");
		hashwords.put("ISSUE","vraacshuu");
		hashwords.put("IT","ke");
		hashwords.put("ITS","kes");
		hashwords.put("JAR","febuu");
		hashwords.put("JESTER","tranivouw");
		hashwords.put("JEWELLER","ligevrhe");
		hashwords.put("JOURNEY","akhloegav");
		hashwords.put("JOY","traraaje");
		hashwords.put("JOYFUL","raaj");
		hashwords.put("JUDGE","soghiste");
		hashwords.put("JUMP","zneenken");
		hashwords.put("JUSTICE","soghe");
		hashwords.put("KIND","mezl");
		hashwords.put("KINDLY","mezl-eh");
		hashwords.put("KILL","zvejan");
		hashwords.put("KITCHEN","goegwiekfuu");
		hashwords.put("KNEE","vaguu");
		hashwords.put("KNEEL","glafen");
		hashwords.put("KNIFE","vahvruu");
		hashwords.put("KNOW","secan");
		hashwords.put("LADDER","snilhuu");
		hashwords.put("LAND","vuhvuu");
		hashwords.put("LANDSLIDE","blajliyookuu");
		hashwords.put("LANGUAGE","doscisme");
		hashwords.put("LARGE","jarh");
		hashwords.put("LASTLY","fints-eh");
		hashwords.put("LAVA","slukluu");
		hashwords.put("LEAF","numpuu");
		hashwords.put("LEAFLESS","numpimarh");
		hashwords.put("LEAFY","numpust");
		hashwords.put("LEARN","krasan");
		hashwords.put("LEAVE","gren");
		hashwords.put("LEG","joejuu");
		hashwords.put("LEMON","zerajuu");
		hashwords.put("LESS","suuft");
		hashwords.put("LESSON","krasakluu");
		hashwords.put("LETTER","beec");
		hashwords.put("LIFE","karm");
		hashwords.put("LIGHT","ethobluu");
		hashwords.put("LIGHTS","khek");
		hashwords.put("LIGHTNING","slinte");
		hashwords.put("LIKE","fuust");
		hashwords.put("LINE","froucuu");
		hashwords.put("LISTEN","dheban");
		hashwords.put("LISTEN","noi'haelnan");
		hashwords.put("LISTENER","aelnath");
		hashwords.put("LIVE","bekarmen");
		hashwords.put("LIZARD","coeshke");
		hashwords.put("LOCATION","pololhguu");
		hashwords.put("LOOK","gluefan");
		hashwords.put("LOOKS","Vleemen");
		hashwords.put("LONG","viej");
		hashwords.put("LOSE","hezdan");
		hashwords.put("LOUDLY","tov-eh");
		hashwords.put("LUSH","numbvalts");
		hashwords.put("MACHINE","batuu");
		hashwords.put("MAGIC","draafuu");
		hashwords.put("MAKE","butsan");
		hashwords.put("MANSION","roubistrhuu");
		hashwords.put("MANY","toum");
		hashwords.put("MARK","cublan");
		hashwords.put("MARKET","nuhgzuu");
		hashwords.put("MARRIAGE","bielakel");
		hashwords.put("MARRY","bielan");
		hashwords.put("MASCULINE","loifalb");
		hashwords.put("MATTER","vraacshuu");
		hashwords.put("ME","ciy");
		hashwords.put("MEAT","shievuu");
		hashwords.put("MEDICINE","gyi'huu");
		hashwords.put("MEET","gaeshan");
		hashwords.put("MELEE","bwoefohpuu");
		hashwords.put("MERCHANT","nuhgzevrhe");
		hashwords.put("MERRIMENT","traraaje");
		hashwords.put("MESSAGE","khavyuu");
		hashwords.put("MESSAGEBOARD","doeftkhavyuu");
		hashwords.put("METAL","jaevmuu");
		hashwords.put("METER","jaakuu");
		hashwords.put("MIDNIGHT","yaezluu");
		hashwords.put("MIGHT","tog");
		hashwords.put("MILK","benhuu");
		hashwords.put("MINIMAL","zgeg");
		hashwords.put("MINUTE","dhoeruu");
		hashwords.put("MODEL","sohmuu");
		hashwords.put("MONEY","numpuu");
		hashwords.put("MONSTER","leshpe");
		hashwords.put("MONTH","tecuu");
		hashwords.put("MORE","toum");
		hashwords.put("MOST","toum");
		hashwords.put("MOTHER","moencone");
		hashwords.put("MOTHER","noimoeman");
		hashwords.put("MOUNTAIN","soufwhuu");
		hashwords.put("MOUNTAIN","soufwhuu");
		hashwords.put("MUD","sleghuu");
		hashwords.put("MUG","ladhuu");
		hashwords.put("MUSCLE","ghimuu");
		hashwords.put("MUSIC","nounshuu");
		hashwords.put("MUSICAL","nounshalb");
		hashwords.put("MUSICALLY","nounshalb-eh");
		hashwords.put("MUSICIAN","nounshathe");
		hashwords.put("MUST","hont");
		hashwords.put("MY","cen");
		hashwords.put("NADA","zerhu");
		hashwords.put("NAME","herhuu");
		hashwords.put("NARROW","hal");
		hashwords.put("NEARBY","shas");
		hashwords.put("NECK","me'he");
		hashwords.put("NEED","stoukan");
		hashwords.put("NEPHEW","karise");
		hashwords.put("NEW","cot");
		hashwords.put("NEXT","dene");
		hashwords.put("NIECE","karafe");
		hashwords.put("NIL","zerhu");
		hashwords.put("NINE","nuunu");
		hashwords.put("NO","nash");
		hashwords.put("NOISE","januu");
		hashwords.put("NONE","nied");
		hashwords.put("NOR","en");
		hashwords.put("NOW","pret-eh");
		hashwords.put("NOTICE","gluefan");
		hashwords.put("NULL","zerhu");
		hashwords.put("NURSERY","basataguu");
		hashwords.put("NUT","faukuu");
		hashwords.put("OCCUR","khuaken");
		hashwords.put("OFFICE","trespeefuu");
		hashwords.put("OLD","vel");
		hashwords.put("ONE","onu");
		hashwords.put("ORACLE","soghathrhe");
		hashwords.put("ORB","laebuu");
		hashwords.put("ORBIT","jinsuu");
		hashwords.put("ORCHESTRA","nounshove");
		hashwords.put("ORDER","agizruu");
		hashwords.put("ORDERLY","talk");
		hashwords.put("OTHER","bozaa");
		hashwords.put("OTHERWISE","most");
		hashwords.put("OUGHT","tuum");
		hashwords.put("OUR","cozen");
		hashwords.put("OUT","dost");
		hashwords.put("OVEN","begmuu");
		hashwords.put("OVER","vos");
		hashwords.put("PALACE","roubistrhuu");
		hashwords.put("PALM","laujuu");
		hashwords.put("PAINT","zhablan");
		hashwords.put("PAINTER","zhabliste");
		hashwords.put("PAINTING","zhablakuu");
		hashwords.put("PARENT","nonhe");
		hashwords.put("PARTY","bustan");
		hashwords.put("PASS","rasluu");
		hashwords.put("PATH","kliy");
		hashwords.put("PATIENT","jaarvakle");
		hashwords.put("PAW","ezekuu");
		hashwords.put("PAY","pajan");
		hashwords.put("PAYEE","pajakle");
		hashwords.put("PAYER","pajatrhe");
		hashwords.put("PAYMENT","akhpaj");
		hashwords.put("PEOPLE","galn'hiete");
		hashwords.put("PERSON","galne");
		hashwords.put("PICKPOCKET","brolhist");
		hashwords.put("PILLAR","cuezhnuu");
		hashwords.put("PISS","ma'haelhuu");
		hashwords.put("PISTOL","mokhuu");
		hashwords.put("PITCHER","palshiste");
		hashwords.put("PK","mahcantsuu");
		hashwords.put("PLACE","pololhg");
		hashwords.put("PLAIN","cuskuu");
		hashwords.put("PLAINS","cuskuu");
		hashwords.put("PLANET","cohmpuu");
		hashwords.put("PLASTIC","sootruu");
		hashwords.put("PLATE","inkrhuu");
		hashwords.put("PLAY","noinounshan");
		hashwords.put("PLEAD","di'tuktlen");
		hashwords.put("PLEASE","vesk");
		hashwords.put("PLOUGH","oigyaaran");
		hashwords.put("PLOW","noideefsan");
		hashwords.put("POISON","narhma'huu");
		hashwords.put("POLE","goshtruu");
		hashwords.put("POOR","zgeg");
		hashwords.put("POSSESS","stigan");
		hashwords.put("POT","febuu");
		hashwords.put("POTATO","prhipfuu");
		hashwords.put("POTION","gyi'huu");
		hashwords.put("PRAIRIE","cuskuu");
		hashwords.put("PRECEDING","debe");
		hashwords.put("PREFER","vijan");
		hashwords.put("PREPARE","akhcaeb");
		hashwords.put("PREPARES","caeban");
		hashwords.put("PRESENT","zguprhuu");
		hashwords.put("PREVIOUS","huuc");
		hashwords.put("PREVIOUSLY","huuc-eh");
		hashwords.put("PRISON","khipfhietuu");
		hashwords.put("PRISONER","kaptakle");
		hashwords.put("PRISONER","kaptakle");
		hashwords.put("PROSTITUTE","sharhathe");
		hashwords.put("PROSTITUTION","shartuumuu");
		hashwords.put("PROVIDE","toprhan");
		hashwords.put("PROVISIONS","toprholhguu");
		hashwords.put("PUNISH","ciswhan");
		hashwords.put("PUNISHER","ciswhiste");
		hashwords.put("PUNISHMENT","ciswhoge");
		hashwords.put("PUT","vihblan");
		hashwords.put("QUESTION","dhoetrhopuu");
		hashwords.put("QUIET","im");
		hashwords.put("QUIETLY","im-eh");
		hashwords.put("RABBIT","leprhe");
		hashwords.put("RADIO","ojoikluu");
		hashwords.put("RAGE","zhgishte");
		hashwords.put("RAINING","noupethan");
		hashwords.put("RAIN","pethuu");
		hashwords.put("RANCH","shlepuu");
		hashwords.put("RANGER","zetsiste");
		hashwords.put("REACTION","vrhoshmuu");
		hashwords.put("READ","ekinan");
		hashwords.put("REAL","vosalb");
		hashwords.put("REALITY","vosuu");
		hashwords.put("REALLY","vosalb-eh");
		hashwords.put("RECALL","britismuu");
		hashwords.put("RED","tint");
		hashwords.put("REGRET","agraasnuu");
		hashwords.put("REGRETS","graasan");
		hashwords.put("REMAIN","bekan");
		hashwords.put("REPAIR","pican");
		hashwords.put("REPAIRER","gglounezhliste");
		hashwords.put("REPEATEDLY","gloef-eh");
		hashwords.put("REQUEST","agizehr");
		hashwords.put("REQUIRE","stoukan");
		hashwords.put("REQUIREMENT","stoukizmuu");
		hashwords.put("RESOURCE","poogrhuu");
		hashwords.put("RESPECT","abdan");
		hashwords.put("RESPECTFUL","abdistisp");
		hashwords.put("RESTAURANT","goekfosuu");
		hashwords.put("RESTRAINT","ajetuu");
		hashwords.put("RICE","maeyuu");
		hashwords.put("RICH","uegafst");
		hashwords.put("RICHLY","uegavuu");
		hashwords.put("RIFLE","mokhuu");
		hashwords.put("RIP","velban");
		hashwords.put("RIPE","upibm");
		hashwords.put("RIVER","dhounuu");
		hashwords.put("ROAD","kliyuu");
		hashwords.put("ROBBER","loiflaeye");
		hashwords.put("ROCK","yookuhrhuu");
		hashwords.put("ROCKY","yookuhrhst");
		hashwords.put("ROOF","fuejuu");
		hashwords.put("ROOM","ga'huu");
		hashwords.put("ROPE","naskuu");
		hashwords.put("ROT","thuzden");
		hashwords.put("RUN","strimen");
		hashwords.put("SAD","zhmekh");
		hashwords.put("SADNESS","trazhmekhe");
		hashwords.put("SAIL","zetsan");
		hashwords.put("SAILOR","osemevrhe");
		hashwords.put("SALOON","goekceguu");
		hashwords.put("SAME","feeg");
		hashwords.put("SAY","meeven");
		hashwords.put("SCARE","rhiknan");
		hashwords.put("SCATTER","paunan");
		hashwords.put("SCHOOL","krasovnisuu");
		hashwords.put("SCREAM","tas'uhshpieten");
		hashwords.put("SEA","maale");
		hashwords.put("SEASON","rhofnuu");
		hashwords.put("SEAT","asov");
		hashwords.put("SECTION","lhikuu");
		hashwords.put("SEE","gluefan");
		hashwords.put("SEED","brayuu");
		hashwords.put("SELECT","trugan");
		hashwords.put("SELECTION","trugoisuu");
		hashwords.put("SERVE","tsaughan");
		hashwords.put("SERVICE","tsaugholhguu");
		hashwords.put("SETTLE","eragen");
		hashwords.put("SEVEN","aderu");
		hashwords.put("SEX","sharhuu");
		hashwords.put("SHAKE","fuvzan");
		hashwords.put("SHALL","goudh");
		hashwords.put("SHAMAN","draafathe");
		hashwords.put("SHARE","dhruglan");
		hashwords.put("SHE","afke");
		hashwords.put("SHINY","bludr");
		hashwords.put("SHIP","osemjarhuu");
		hashwords.put("SHIRT","kuuthuu");
		hashwords.put("SHOP","magzuu");
		hashwords.put("SHORT","cugrh");
		hashwords.put("SHORT","jyek");
		hashwords.put("SHOULD","gaz");
		hashwords.put("SHOULDER","vaguu");
		hashwords.put("SHOUT","zavben");
		hashwords.put("SHRED","velban");
		hashwords.put("SIBLING","husplozde");
		hashwords.put("SIGHT","gluefismuu");
		hashwords.put("SISTER","lozde");
		hashwords.put("SIT","asen");
		hashwords.put("SITUATION","pletuu");
		hashwords.put("SIX","dizu");
		hashwords.put("SKY","bleshnuu");
		hashwords.put("SLEEP","talen");
		hashwords.put("SLEEPY","atalants");
		hashwords.put("SLOWLY","sham-eh");
		hashwords.put("SMARMY","ohslifl");
		hashwords.put("SMITH","butsan");
		hashwords.put("SNAKE","bokhre");
		hashwords.put("SNAKE","noibokhren");
		hashwords.put("SNOW","nouswujan");
		hashwords.put("SO","brois");
		hashwords.put("SOAK","sekma'hivan");
		hashwords.put("SOCIAL","jaft");
		hashwords.put("SOCIALS","jaftuu");
		hashwords.put("SOFT","angrh");
		hashwords.put("SOME","shonaa");
		hashwords.put("SON","vlefre");
		hashwords.put("SOMEWHERE","shuuzau");
		hashwords.put("SOUND","januu");
		hashwords.put("SPEAK","kegen");
		hashwords.put("SPERICAL","laebalb");
		hashwords.put("SPHERE","laebuu");
		hashwords.put("SPIRE","goshtruu");
		hashwords.put("SPIT","hasuu");
		hashwords.put("SPIT","noi'hasen");
		hashwords.put("SPRING","dhounalhetuu");
		hashwords.put("STAR","nazluu");
		hashwords.put("START","britan");
		hashwords.put("STARVE","traghan");
		hashwords.put("STARVATION","atraghuu");
		hashwords.put("STATE","vuhvuu");
		hashwords.put("STATUE","sohmuu");
		hashwords.put("STEAM","ezezhmohpuu");
		hashwords.put("STEEP","ghac");
		hashwords.put("STEER","zetsan");
		hashwords.put("STEERER","zetsiste");
		hashwords.put("STEPPE","cuskuu");
		hashwords.put("STERNLY","eekim-eh");
		hashwords.put("STICK","pouzluu");
		hashwords.put("STOMACH","osucuu");
		hashwords.put("STONE","yookuu");
		hashwords.put("STONY","yookalb");
		hashwords.put("STOP","glaa");
		hashwords.put("STORY","vuhsuu");
		hashwords.put("STRAIGHT","usth");
		hashwords.put("STREAM","dhounetuu");
		hashwords.put("STREET","kliyhietuu");
		hashwords.put("STRENGTH","trakhajuu");
		hashwords.put("STRONG","khaj");
		hashwords.put("STRONGLY","khaj-eh");
		hashwords.put("STUDENT","krasiste");
		hashwords.put("SUBWAY","poe'hajvituu");
		hashwords.put("SUFFER","glikan");
		hashwords.put("SUGAR","emiegvuu");
		hashwords.put("SUN","wage");
		hashwords.put("SUNLIGHT","jevuu");
		hashwords.put("SUNSHINE","jevuu");
		hashwords.put("SUPPLIES","toprholhguu");
		hashwords.put("SUPPLY","toprhan");
		hashwords.put("SURE","lesl");
		hashwords.put("SURROUND","celky");
		hashwords.put("SWAP","rheegrhan");
		hashwords.put("SWIM","alugzh");
		hashwords.put("SWIMS","lugzhen");
		hashwords.put("SYMPATHIZE","noimeghan");
		hashwords.put("TABLE","blomuu");
		hashwords.put("TAILOR","donatrhe");
		hashwords.put("TAKE","shokrhan");
		hashwords.put("TALK","kegen");
		hashwords.put("TAME","mawan");
		hashwords.put("TAMED","maw");
		hashwords.put("TAR","braupuu");
		hashwords.put("TEA","mauvyuu");
		hashwords.put("TEACH","dvuundan");
		hashwords.put("TEAR","velban");
		hashwords.put("TEMPLE","daesovuu");
		hashwords.put("TEN","oki");
		hashwords.put("THANK","budzdakan");
		hashwords.put("THANKS","dak");
		hashwords.put("THAT","evd");
		hashwords.put("THEFT","yabluu");
		hashwords.put("THEIR","brhen");
		hashwords.put("THEM","brhiy");
		hashwords.put("THEN","man");
		hashwords.put("THERE","valau");
		hashwords.put("THEY","brhek");
		hashwords.put("THICK","foeddh");
		hashwords.put("THIEF","yablathe");
		hashwords.put("THIEVERY","yabluu");
		hashwords.put("THIN","coik");
		hashwords.put("THINNING","hal");
		hashwords.put("THING","bal");
		hashwords.put("THREE","besu");
		hashwords.put("THROAT","me'he");
		hashwords.put("THROUGH","dezde");
		hashwords.put("THROW","apalsh");
		hashwords.put("THROWS","palshan");
		hashwords.put("THUS","uuj");
		hashwords.put("TIDY","sekstuaban");
		hashwords.put("TIME","skesuu");
		hashwords.put("TINKER","kliyevrhe");
		hashwords.put("TINY","nis");
		hashwords.put("TODAY","restasuu");
		hashwords.put("TOMORROW","gvugyuu");
		hashwords.put("TOOTH","rhuuluu");
		hashwords.put("TORSO","cesuu");
		hashwords.put("TOUCH","abrolhuu");
		hashwords.put("TOWER","conhuu");
		hashwords.put("TOWERS","zecrhuu");
		hashwords.put("TOWN","vaeyuu");
		hashwords.put("TRACK","kliyet");
		hashwords.put("TRAIN","poe'hajarhuu");
		hashwords.put("TRANSPORT","poe'han");
		hashwords.put("TRAVEL","loegven");
		hashwords.put("TREE","rhabuu");
		hashwords.put("TRENCHCOAT","lufluu");
		hashwords.put("TRIBE","galn'hiete");
        hashwords.put("GANG","galn'hiete");
		hashwords.put("TRUE","vosalb");
		hashwords.put("TRUELY","vosalb-eh");
		hashwords.put("TRUTH","vosuu");
		hashwords.put("TURKEY","oh'heele");
		hashwords.put("TWO","drasu");
		hashwords.put("UNCLE","zgate");
		hashwords.put("UNDER","suu");
		hashwords.put("UNDERNEATH","suusy");
		hashwords.put("UNFORGIVING","eekim");
		hashwords.put("UNFRIENDLY","ohslumb");
		hashwords.put("UNIVERSITY","krasovjarhuu");
		hashwords.put("UNSURE","narhlesl");
		hashwords.put("UNTIL","ista");
		hashwords.put("UNWISE","twing");
		hashwords.put("UNWISELY","twing-eh");
		hashwords.put("UP","tob");
		hashwords.put("UPON","zhuusy");
		hashwords.put("UPWARDS","toby");
		hashwords.put("URINE","ma'haelhuu");
		hashwords.put("US","coziy");
		hashwords.put("USE","gotan");
		hashwords.put("VALLEY","nausuu");
		hashwords.put("VIA","gles");
		hashwords.put("VIEW","gluefan");
		hashwords.put("VILLAGE","eragolhguu");
		hashwords.put("VILLAGERS","shlepietathe");
		hashwords.put("VISIONAIRY","soghathrhe");
		hashwords.put("VISITS","akhsloth");
		hashwords.put("VISIT","slothan");
		hashwords.put("VOLCANO","zeesuu");
		hashwords.put("WAGES","pajdomuu");
		hashwords.put("WALK","trhaden");
		hashwords.put("WALL","doeftuu");
		hashwords.put("WANT","gruugan");
		hashwords.put("WARLOCK","jaarviste");
		hashwords.put("WARM","noizh");
		hashwords.put("WAS","en");
		hashwords.put("WASH","khoivan");
		hashwords.put("WASTE","gvuljan");
		hashwords.put("WATER","ma'huu");
		hashwords.put("WATER","noima'han");
		hashwords.put("WATERFALL","blajlima'huu");
		hashwords.put("WATERY","ma'hust");
		hashwords.put("WE","cozelh");
		hashwords.put("WEALTH","uegafst");
		hashwords.put("WEALTHY","uegavuu");
		hashwords.put("WEEK","vahmuu");
		hashwords.put("WEEP","wihdren");
		hashwords.put("WELL","looghuu");
		hashwords.put("WERE","en");
		hashwords.put("WET","ciel");
		hashwords.put("WHAT","gziy");
		hashwords.put("WHEAT","sastrhuu");
		hashwords.put("WHERE","badh");
		hashwords.put("WHISPER","fnitlen");
		hashwords.put("WHITE","aakh");
		hashwords.put("WHO","gze");
		hashwords.put("WHOSE","gzen");
		hashwords.put("WIDE","foeddh");
		hashwords.put("WIFE","tothpe");
		hashwords.put("WILD","namaw");
		hashwords.put("WILDLY","namaw-eh");
		hashwords.put("WILL","nezh");
		hashwords.put("WIND","brishnuu");
		hashwords.put("WINE","rhaesuu");
		hashwords.put("WIRELESS","ojoikluu");
		hashwords.put("WISELY","steev-eh");
		hashwords.put("WITCH","jaarviste");
		hashwords.put("WITHOUT","deth");
		hashwords.put("WITHOUT","di");
		hashwords.put("WIZARD","draafath");
		hashwords.put("WOMAN","gyane");
		hashwords.put("WOMB","khuane");
		hashwords.put("WONDER","oomuhfuu");
		hashwords.put("WONDEROUS","oomuhfeshp");
		hashwords.put("WORD","wadoscuu");
		hashwords.put("WORK","akhueb");
		hashwords.put("WORKER","khuebatrhe");
		hashwords.put("WORKSHOP","khuebeefuu");
		hashwords.put("WORLD","cohmap");
		hashwords.put("WORM","rhieye");
		hashwords.put("WORTHLESS","dozdhoune");
		hashwords.put("WOULD","haz");
		hashwords.put("WRIST","vaguu");
		hashwords.put("WRITE","trespan");
		hashwords.put("XP","noishloektan");
		hashwords.put("YEAR","jinsuu");
		hashwords.put("YELL","zavben");
		hashwords.put("YELLOW","uekh");
		hashwords.put("YESTERDAY","rezdenuu");
		hashwords.put("YES","shas");
		hashwords.put("YET","fyal");
		hashwords.put("YOU","diy");
		hashwords.put("YOU","feselh");
		hashwords.put("YOUNG","cot");
		hashwords.put("YOUR","fesen");
		hashwords.put("YOURS","fesiy");
		hashwords.put("ZERO","zerhu");
		return hashwords;
		}
}