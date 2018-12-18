package com.tkohdk.postfixnotaion.calculator;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

/**
 * Created by takeoh on 2017/08/30.
 */
public class BigDecimalCalculatorTest {
    @Test
    public void involution() throws Exception {
        BigDecimalCalculator sc = new BigDecimalCalculator( new MathContext(12, RoundingMode.HALF_EVEN));
        BigDecimal actual = sc.involution(new BigDecimal("2"),new BigDecimal("3"));
        assertEquals(8, actual.intValue());

        actual = sc.involution(new BigDecimal("3"),new BigDecimal("2"));
        assertEquals(9, actual.intValue());

        actual = sc.involution(new BigDecimal("2"),new BigDecimal("-1"));
        assertEquals(new BigDecimal("0.5"), actual);

        actual = sc.involution(new BigDecimal("2"),new BigDecimal("-1.9"));
        assertEquals(new BigDecimal("0.2679433656340732827771944357664324343204498291015625"), actual);

        actual = sc.involution(new BigDecimal("12"),new BigDecimal("555.5"));
        assertEquals(new BigDecimal("305620287164289491083338234589525332829398430097009502230905623926321539458789313347396481768388871260198647936808531745514634846668814719441516867055812794019069556415223827638679817179142794427022507160547160946272405205972800258013375702610388839340955457713203546192678434230715223660146548257569510413779444226316393404854730704719918223454306483387152177334061163645691872754239198989171844167075684657267011491345129565248858339912244394311066250624980304921936274843953629385968232518515101590302629224998717516008553061644174048987981662182418078862000261933443536472876969903350095850504192"), actual);

        actual = sc.involution(new BigDecimal("2"),new BigDecimal("-55.5"));
        assertEquals(new BigDecimal("1.9626155733547189584023732449575123249504444622915051710254630279450793750584125518798828125E-17"), actual);
    }

    @Test
    public void factorial() throws Exception {
        BigDecimalCalculator sc = new BigDecimalCalculator( new MathContext(12, RoundingMode.HALF_EVEN));
        BigDecimal actual = sc.factorial(new BigDecimal("4"));
        assertEquals(24, actual.intValue());

        actual = sc.factorial(new BigDecimal("5"));
        assertEquals(120, actual.intValue());

        actual = sc.factorial(new BigDecimal("10"));
        assertEquals(3628800, actual.intValue());

        actual = sc.factorial(new BigDecimal("15"));
        assertEquals(new BigDecimal("1307674368000"), actual);

        // 【参考】
        // https://www.nap.st/factorial_calculation/?lang=ja
        //
        actual = sc.factorial(new BigDecimal("16"));
        assertEquals(new BigDecimal("20922789888000"), actual);

        actual = sc.factorial(new BigDecimal("17"));
        assertEquals(new BigDecimal("355687428096000"), actual);

        actual = sc.factorial(new BigDecimal("18"));
        assertEquals(new BigDecimal("6402373705728000"), actual);

        actual = sc.factorial(new BigDecimal("19"));
        assertEquals(new BigDecimal("121645100408832000"), actual);

        actual = sc.factorial(new BigDecimal("20"));
        assertEquals(new BigDecimal("2432902008176640000"), actual);

        actual = sc.factorial(new BigDecimal("30"));
        assertEquals(new BigDecimal("265252859812191058636308480000000"), actual);

        actual = sc.factorial(new BigDecimal("40"));
        assertEquals(new BigDecimal("815915283247897734345611269596115894272000000000"), actual);

        actual = sc.factorial(new BigDecimal("50"));
        assertEquals(new BigDecimal("30414093201713378043612608166064768844377641568960512000000000000"), actual);

        actual = sc.factorial(new BigDecimal("60"));
        assertEquals(new BigDecimal("8320987112741390144276341183223364380754172606361245952449277696409600000000000000"), actual);

    }



    @Test
    public void add() throws Exception {
        BigDecimalCalculator sc = new BigDecimalCalculator(new MathContext(12, RoundingMode.HALF_EVEN));

        {
            BigDecimal bd = sc.add(new BigDecimal("1"), new BigDecimal("2"));
            assertEquals(bd.intValue(), 3);
        }
//        {
//            BigDecimal bd = sc.add("1,000", "2,000");
//            assertEquals(bd.intValue(), 3000);
//        }
    }

    @Test
    public void subtract() throws Exception {
        BigDecimalCalculator sc = new BigDecimalCalculator(new MathContext(12, RoundingMode.HALF_EVEN));
        BigDecimal bd = sc.subtract(new BigDecimal("1"),new BigDecimal("2"));
        assertEquals(new BigDecimal("-1"),bd);
    }

    @Test
    public void multiply() throws Exception {
        BigDecimalCalculator sc = new BigDecimalCalculator(new MathContext(12, RoundingMode.HALF_EVEN));

        BigDecimal bd = sc.multiply(new BigDecimal("1"),new BigDecimal("2"));
        assertEquals(new BigDecimal("2"),bd);
    }

    @Test
    public void divide() throws Exception {
        BigDecimalCalculator sc = new BigDecimalCalculator(new MathContext(12, RoundingMode.HALF_EVEN));

        BigDecimal bd = sc.calculate("/",new BigDecimal("1"),new BigDecimal("2"));
        assertEquals(new BigDecimal("0.5"), bd.stripTrailingZeros());
    }

    @Test
    public void calculate() throws Exception {
        BigDecimalCalculator sc = new BigDecimalCalculator(new MathContext(12, RoundingMode.HALF_EVEN));

        BigDecimal bd = sc.calculate("+",new BigDecimal("1"),new BigDecimal("2"));
        assertEquals(3, bd.intValue());

        bd = sc.calculate("*",new BigDecimal("1"),new BigDecimal("2"));
        assertEquals(new BigDecimal("2"), bd);

        bd = sc.calculate("-",new BigDecimal("1"),new BigDecimal("2"));
        assertEquals(new BigDecimal("-1"), bd);

        bd = sc.calculate("/",new BigDecimal("1"),new BigDecimal("2"));
        assertEquals(new BigDecimal("0.5"), bd.stripTrailingZeros());

        bd = sc.calculate("^",new BigDecimal("5"),new BigDecimal("5"));
        assertEquals(new BigDecimal("3125"), bd.stripTrailingZeros());


        String ans = "1314080690631997019001380075176934353055854544789849183993885309968452531906605976601808352198061788932197266474312375208017269073444126781964087571365440785339172817067773243244978455422382729643321780768573579387642963549919336091951534545089510464956546529860270449625451743897413292119246034500996287926345839027724369236215086369158886734523080269853525471310914787649680264655868263504876764753654175610529398997918244804485893960976958425389640903162765813970959268700377754368879638265773493708337469484836509703996198399441021419519568937847672749019643324502859361805447622290270471454773627654877595983125473382367052090145011411212983219876297336028934913363025057626442369648443080077324657568133436802052487643470454239621442494999680306341433821023526138112344594573328812393513693013454241635841709827373356504754226093947538861041847899897720775064185836144190085028035825072300067031749265587150844530161714094303044378507702057560770797227939881041719741581092796501065724681316490794376739624246097457819681472294005205024834551992686343159093115565414269278557324545333104014826835874441645057655961791895254499408886698641247942359574144588038898602767823475036805334541948673262676039844887674317944620618972092263224711551787216305343050216463639638043317737688238739612185308754575838647837718298798165363860918425103140087838452040788484857814089454589731161935244666507969505068497968368425950263437837060137862713051871333774903680484623179326195515323874354210591819253166597542942081429273091277658568019105596749132425056934721579707986712494252708312025647076718923505676497713659058002310605983890712155516121898891662609942900023149150954229418249543721534800599502485280968909413760565181982908863167035078697751867453278188650994630472783736252489277759915158010798262577078734718466915769537067402256727824691940745307896965507887534791878694119306294249103004302376958129558021413660451347912640685384185541712009875413129969509953305734166719956166326773095125564783464537927744191852243509380718170012615296758507641459705831146272867701171077541279152097503221218763240858548732678988734800506722884963946559728254189870833892589903834716371487029563834805691705247034676776874954686536176889615776209962572926977575874829123065444695933548227414145158719097459733133977187971512951953186385486829599158283992238593163577932943291490124974664150065655566301752420424798118749301275156792188470081556568563576063467126822558830156553566806903746093705180898016085176114822637617610711263757517062236982524309899293025356415134874574684821473127216294426865679663641945191437533227033708608986201922166100443465179598650992104112456078533936063603977552850929800600442719292752344891212346163339456089961174575228653659926741080105390356876065560539705869476639677361678781654145839234939672536245413";
        bd = sc.calculate("^", new BigDecimal("333333333"),new BigDecimal( "333"));
        assertEquals(new BigDecimal(ans), bd);


    }

    @Test
    public void sin() {
        BigDecimalCalculator sc = new BigDecimalCalculator(new MathContext(12, RoundingMode.HALF_EVEN));

        BigDecimal bd = sc.sin(new BigDecimal("1"));
        assertEquals(new BigDecimal("0.8414709848078965048756572286947630345821380615234375"), bd.stripTrailingZeros());
    }

    @Test
    public void cos() {
        BigDecimalCalculator sc = new BigDecimalCalculator(new MathContext(12, RoundingMode.HALF_EVEN));

        BigDecimal bd = sc.cos(new BigDecimal("1"));
        assertEquals(new BigDecimal("0.540302305868139765010482733487151563167572021484375"), bd.stripTrailingZeros());

    }

    @Test
    public void tan() {
        BigDecimalCalculator sc = new BigDecimalCalculator(new MathContext(12, RoundingMode.HALF_EVEN));

        BigDecimal bd = sc.tan(new BigDecimal("1"));
        assertEquals(new BigDecimal("1.557407724654902292371616567834280431270599365234375"), bd.stripTrailingZeros());

    }

    @Test
    public void log() {
        BigDecimalCalculator sc = new BigDecimalCalculator(new MathContext(12, RoundingMode.HALF_EVEN));

        BigDecimal bd = sc.log(new BigDecimal("200"));
        assertEquals(new BigDecimal("5.29831736654803631125787433120422065258026123046875"), bd.stripTrailingZeros());

    }

    @Test
    public void log10() {
        BigDecimalCalculator sc = new BigDecimalCalculator(new MathContext(12, RoundingMode.HALF_EVEN));

        BigDecimal bd = sc.log10(new BigDecimal("200"));
        assertEquals(new BigDecimal("2.301029995663981253528618253767490386962890625"), bd.stripTrailingZeros());

    }
}