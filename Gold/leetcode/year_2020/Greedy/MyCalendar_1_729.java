package com.leetcode.year_2020.Greedy;

import com.util.LogUtil;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * @author neeraj on 19/10/19
 * Copyright (c) 2019, data-structures.
 * All rights reserved.
 */
public class MyCalendar_1_729 {

    static class MyCalendarWithTreeMap {
        TreeMap<Integer, Integer> calendar;

        public MyCalendarWithTreeMap() {
            this.calendar = new TreeMap<>();
        }

        public boolean book(int start, int end) {
            // Latest start of meeting immediately less than the start of this new meeting
            Integer maximumStartLessThanStartOfNewBooking = this.calendar.floorKey(start);
            if (maximumStartLessThanStartOfNewBooking != null && this.calendar.get(maximumStartLessThanStartOfNewBooking) > start)
                return false;
            // Earliest start of meeting immediately greater than the start of this new meeting
            Integer minimumStartGreaterThanStartOfNewBooking = this.calendar.ceilingKey(start);

            // if this earliest start of meeting is less than the end of new booking then there is clash.
            if (minimumStartGreaterThanStartOfNewBooking != null && minimumStartGreaterThanStartOfNewBooking < end)
                return false;
            this.calendar.put(start, end);
            return true;
        }
    }

    static class MyCalendar {
        private PriorityQueue<Booking> minHeap1;
        private PriorityQueue<Booking> backupMinHeap;

        static class Booking {
            int start;
            int end;

            public Booking(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }

        public MyCalendar() {
            minHeap1 = new PriorityQueue<>(Comparator.comparingInt(a -> a.end));
            backupMinHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.end));
        }

        public boolean book(int start, int end) {
            // First check if minHeap1 is empty then we can just put the booking and rest;
            if (minHeap1.isEmpty()) {
                minHeap1.add(new Booking(start, end));
                return true;
            }
            Booking earliestFinishingBooking;
            // Now comes the interesting part when minHeap1 is not empty
            while (!minHeap1.isEmpty()) {
                earliestFinishingBooking = minHeap1.poll();

                // 3 things, when earliestEnd < newBookingStart
                if (earliestFinishingBooking.end <= start) {
                    backupMinHeap.add(earliestFinishingBooking);
                    continue;
                }

                // Clashing booking
                if (earliestFinishingBooking.end > start && (earliestFinishingBooking.start <= start || earliestFinishingBooking.start < end)) {

                    // Now let's put everything from backupHeap to this heap
                    minHeap1.add(earliestFinishingBooking);
                    while (!backupMinHeap.isEmpty()) {
                        minHeap1.add(backupMinHeap.poll());
                    }
                    return false;
                } else { // We found
                    // Now let's put everything from backupHeap to this heap
                    minHeap1.add(earliestFinishingBooking);
                    minHeap1.add(new Booking(start, end));
                    while (!backupMinHeap.isEmpty()) {
                        minHeap1.add(backupMinHeap.poll());
                    }
                    return true;
                }
            }

            // This is when we have reached to the end of minHeap1 and didn't find a clashing booking
            // or position to put the new booking.
            if (!backupMinHeap.isEmpty()) {
                backupMinHeap.add(new Booking(start, end));
                minHeap1 = backupMinHeap;
                backupMinHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.end));
            }
            return true;
        }
    }

    private static void testMyCalendar(int[][] bookings) {
        MyCalendar calendar = new MyCalendar();
        for (int i = 0; i < bookings.length; i++) {
            System.out.println("Meeting [" + bookings[i][0] + "," + bookings[i][1] + "] ==> " + calendar.book(bookings[i][0], bookings[i][1]));
        }
    }

    private static void testMyCalendarWithTreeMap(int[][] bookings) {
        MyCalendarWithTreeMap calendar = new MyCalendarWithTreeMap();
        for (int i = 0; i < bookings.length; i++) {
            System.out.println("Meeting [" + bookings[i][0] + "," + bookings[i][1] + "] ==> " + calendar.book(bookings[i][0], bookings[i][1]));
        }
    }

    public static void main(String[] args) {
//        MyCalendar object = new MyCalendar();
//        System.out.println(object.book(10, 20));
//        System.out.println(object.book(25, 30));
//        System.out.println(object.book(20, 25));
//        System.out.println(object.book(18, 22));

        int[][] bookings = new int[][]{
                {47, 50}, {33, 41}, {39, 45}, {33, 42}, {25, 32}, {26, 35}, {19, 25}, {3, 8}, {8, 13}, {18, 27}
        };
//        testMyCalendar(bookings);
        testMyCalendarWithTreeMap(bookings);

        LogUtil.logIt("New Booking =======================================>", true);

        bookings = new int[][]{
                {710661, 716484}, {194195, 201777}, {692210, 698824}, {689554, 694278}, {412401, 417138}, {865223, 871345},
                {254866, 261937}, {800181, 807048}, {488445, 493603}, {639644, 647327}, {429721, 435311}, {664049, 668313},
                {700828, 707055}, {226210, 231246}, {829509, 833723}, {50166, 54266}, {888826, 895677}, {978328, 983124},
                {622868, 627945}, {699341, 705876}, {53398, 61176}, {799641, 804558}, {498408, 506124}, {178029, 184928},
                {32847, 39353}, {844014, 851414}, {858456, 865546}, {294225, 301912}, {122577, 130085}, {808634, 812660},
                {925465, 932964}, {763895, 769446}, {658724, 665244}, {609184, 613241}, {287932, 293178}, {581469, 588501},
                {891197, 899186}, {898190, 902243}, {498427, 504047}, {266550, 271051}, {560909, 565862}, {38121, 43794},
                {646941, 651166}, {949343, 955384}, {443301, 450814}, {619748, 624453}, {643131, 649622}, {46889, 51766},
                {159130, 165561}, {473372, 480715}, {524040, 529227}, {764130, 772060}, {132347, 138028}, {776212, 784050},
                {795238, 800720}, {599970, 604627}, {348370, 353860}, {338878, 344144}, {69468, 74102}, {25216, 31872},
                {238847, 244285}, {143317, 148121}, {27497, 32379}, {967523, 973951}, {977543, 982961}, {252561, 258500},
                {14432, 18823}, {519779, 525958}, {130013, 136098}, {435619, 440033}, {445656, 452997}, {858697, 863662},
                {261314, 268965}, {21871, 26142}, {202481, 207241}, {171434, 175997}, {593159, 599670}, {847583, 852070},
                {890154, 895312}, {386600, 393671}, {982392, 988904}, {600136, 605613}, {547302, 554842}, {809815, 814031},
                {532980, 539927}, {284072, 289817}, {528317, 533988}, {620947, 625766}, {83955, 88710}, {372574, 380087},
                {884271, 890284}, {173539, 179826}, {58305, 63120}, {974661, 979914}, {1147, 8339}, {804980, 810126},
                {776431, 781648}, {612647, 617045}, {328472, 334001}, {79732, 84535}, {465725, 472368}, {19633, 27002},
                {405454, 410445}, {886691, 893217}, {820644, 824947}, {988678, 994311}, {629722, 636051}, {481116, 485995}, {925268, 930010}, {485346, 491397}, {762265, 769786}, {909188, 915071}, {629373, 635659}, {959952, 967410}, {799813, 807028}, {228625, 234810}, {749912, 755066}, {735171, 742161}, {558532, 566470}, {841628, 845716}, {881489, 887083}, {971210, 978921}, {746278, 751584}, {494926, 501438}, {302286, 307109}, {756652, 762820}, {982338, 987850}, {769383, 775464}, {289541, 295638}, {289902, 295484}, {697608, 702970}, {558424, 562530}, {340839, 347195}, {695156, 700773}, {167038, 173979}, {94866, 102409}, {635825, 642532}, {236688, 242372}, {980559, 985419}, {476676, 483341}, {579366, 585396}, {19158, 24759}, {531474, 536630}, {80612, 86555}, {456067, 463232}, {112771, 120504}, {749194, 755435}, {903594, 911527}, {460592, 466013}, {611748, 619414}, {418970, 426206}, {255246, 260633}, {515327, 522037}, {961365, 967052}, {659231, 663945}, {26776, 32916}, {863875, 870940}, {817628, 823273}, {662249, 669755}, {678164, 682893}, {473598, 478395}, {556659, 561403}, {747769, 752738}, {30198, 36323}, {675976, 680271}, {737977, 745481}, {561628, 568027}, {465391, 471236}, {567416, 573809}, {6948, 11112}, {805997, 812691}, {378805, 386519}, {782391, 786470}, {345030, 349653}, {483419, 490896}, {995386, 1000000}, {107161, 112407}, {744065, 748805}, {22462, 28477}, {867164, 871675}, {124056, 130757}, {232526, 239107}, {107034, 111265}, {393087, 398555}, {139618, 145252}, {280042, 285372}, {581780, 588966}, {886029, 892707}, {249115, 255311}, {161255, 167993}, {593023, 598211}, {175030, 180637}, {959287, 966456}, {724067, 729681}, {843159, 847815}, {644535, 648590}, {58, 7468}, {311947, 317959}, {612811, 620789}, {79921, 85740}, {306015, 311368}, {363071, 370282}, {805000, 812982}, {962119, 966169}, {488626, 494182}, {467342, 474730}, {323203, 329343}, {912616, 918055}, {550561, 555218}, {186972, 192240}, {137872, 145740}, {350730, 357002}, {172724, 179146}, {527218, 534873}, {663733, 668976}, {48795, 53148}, {121639, 126021}, {560272, 566977}, {628835, 634636}, {883556, 888238}, {460090, 468018}, {774223, 781410}, {363233, 369007}, {660622, 665935}, {924388, 931982}, {287668, 294354}, {969927, 974680}, {668279, 673479}, {784053, 789835}, {717441, 721731}, {329403, 337377}, {238984, 243429}, {846458, 853602}, {13962, 21815}, {884209, 888672}, {383805, 391323}, {125532, 129987}, {66592, 72746}, {165493, 172790}, {416310, 421965}, {773584, 778377}, {287919, 294805}, {458914, 463029}, {390987, 398665}, {143643, 149053}, {60017, 66849}, {935279, 939328}, {215768, 223030}, {232845, 236945}, {160596, 167942}, {295057, 300475}, {437017, 442655}, {677751, 683434}, {106481, 111329}, {640344, 646874}, {436789, 441140}, {506871, 512029}, {868051, 875531}, {97569, 102814}, {979635, 986972}, {521857, 527231}, {72966, 79002}, {443819, 449493}, {241079, 247854}, {955010, 962159}, {609020, 613637}, {444100, 451410}, {741753, 745955}, {930180, 937340}, {427345, 434909}, {462035, 467692}, {522649, 529265}, {152324, 157506}, {134823, 142425}, {439591, 445080}, {494018, 499442}, {598709, 603396}, {588557, 592900}, {403510, 407681}, {475198, 482348}, {982458, 988316}, {513250, 518438}, {178894, 185819}, {122481, 129986}, {852151, 860126}, {918369, 924209}, {998525, 1000000}, {886930, 893491}, {167313, 172325}, {610206, 617399}, {424128, 428191}, {242722, 250380}, {936505, 943642}, {674759, 682583}, {308939, 314325}, {128121, 135821}, {270523, 276616}, {983649, 989149}, {174639, 179237}, {760145, 764482}, {621697, 626106}, {216755, 224021}, {354448, 361636}, {434474, 438992}, {438990, 445449}, {711665, 716703}, {159852, 166863}, {981962, 988934}, {131853, 138817}, {788371, 794179}, {240050, 247465}, {224030, 228142}, {585807, 593389}, {54710, 59786}, {14330, 20405}, {215503, 219576}, {61616, 68671}, {255329, 261491}, {955862, 962504}, {735168, 741500}, {203622, 208102}, {930593, 934880}, {31195, 37229}, {836623, 844323}, {204509, 208899}, {313184, 318076}, {539102, 543750}, {31943, 36820}, {349484, 356802}, {177539, 183028}, {441869, 447425}, {316440, 320446}, {641578, 647409}, {443069, 448001}, {314978, 321621}, {353255, 357820}, {820614, 826477}, {674066, 678603}, {184545, 190082}, {798844, 802986}, {314155, 319567}, {184251, 191281}, {104030, 111010}, {621737, 628025}, {697691, 703512}, {709577, 716539}, {968328, 972379}, {945337, 951590}, {651350, 656206}, {653885, 661824}, {647271, 651777}, {260629, 266962}, {230171, 236578}, {54672, 61956}, {782025, 789048}, {289137, 296335}, {692798, 697986}, {108780, 114823}, {194963, 202245}, {297570, 304014}, {921092, 925578}, {217888, 224386}, {126569, 134089}, {949758, 955691}, {352693, 360160}, {338229, 345154}, {855517, 859642}, {288627, 296167}, {841563, 849382}, {982378, 990080}, {619712, 623910}, {668039, 672751}, {259462, 264491}, {656958, 662523}, {151467, 156593}, {177851, 185649}, {902620, 909817}, {152807, 158298}, {167549, 173454}, {151866, 157468}, {659118, 667081}, {656970, 662821}, {641040, 645306}, {911025, 917929}, {483350, 488789}, {317803, 321862}, {18461, 25683}, {139038, 147030}, {623711, 628503}, {840289, 844289}, {171595, 176757}, {917525, 922766}, {265616, 270526}, {200049, 207669}, {141332, 149213}, {26972, 31677}, {562091, 567787}, {667798, 674711}, {595405, 603011}, {845281, 850239}, {110350, 117222}, {345679, 351376}, {821497, 825699}, {742152, 748298}, {212270, 216372}, {147041, 153817}, {845017, 851087}, {525640, 533210}, {854890, 862152}, {435700, 441115}, {357735, 362979}, {456594, 462306}, {193138, 199355}, {942554, 947377}, {526982, 533022}, {679977, 684193}, {436336, 441206}, {495643, 500399}, {168082, 175197}, {400903, 407969}, {512478, 518220}, {442739, 449599}, {774267, 782233}, {631067, 636764}, {896617, 902010}, {729191, 736931}, {52692, 60559}, {278621, 284409}, {659949, 667330}, {405533, 411791}, {409209, 415175}, {298471, 304501}, {452987, 458473}, {616497, 623911}, {765415, 771730}, {237978, 242904}, {640547, 645482}, {283854, 290456}, {160073, 167981}, {884720, 892350}, {965691, 972446}, {187391, 191794}, {843875, 848070}, {399407, 407195}, {976540, 984034}, {709611, 715502}, {358846, 365585}, {10968, 17539}, {817345, 822231}, {457679, 464887}, {257087, 262744}, {914245, 919158}, {944170, 951171}, {947236, 953229}, {692137, 698350}, {325245, 333153}, {364498, 369314}, {145354, 151153}, {45306, 49592}, {914134, 920002}, {20836, 28345}, {243248, 247799}, {134892, 139624}, {317687, 321920}, {594648, 602169}, {262622, 269893}, {173088, 180027}, {792849, 798128}, {594599, 602553}, {54235, 62094}, {838810, 844187}, {832889, 837893}, {255393, 260121}, {264023, 270877}, {760142, 765120}, {590572, 595677}, {168532, 173092}, {495153, 499892}, {762306, 769263}, {747917, 753531}, {121857, 126010}, {743688, 748216}, {380847, 385625}, {740646, 748108}, {495022, 499670}, {585382, 590017}, {82212, 89082}, {87073, 91347}, {747289, 755015}, {748603, 753398}, {63471, 70458}, {237969, 245736}, {811214, 818367}, {64452, 70143}, {575495, 582052}, {640491, 644797}, {719545, 724237}, {268624, 273020}, {30720, 37824}, {428209, 434672}, {61434, 66270}, {668549, 676267}, {988810, 994374}, {548658, 556315}, {576496, 582366}, {834745, 840962}, {919607, 925893}, {69132, 75141}, {497412, 504947}, {339592, 343710}, {60055, 66031}, {682071, 689583}, {257032, 264122}, {641849, 649369}, {523246, 530770}, {438065, 445147}, {153680, 161395}, {157062, 162215}, {783314, 789436}, {561409, 568604}, {746132, 752220}, {792610, 798807}, {998077, 1000000}, {973663, 979343}, {621183, 627293}, {294415, 301779}, {326895, 332461}, {780167, 787288}, {138766, 145816}, {631413, 637072}, {710067, 716526}, {778862, 786788}, {392387, 398685}, {79371, 86382}, {808647, 813193}, {485048, 489681}, {377741, 384461}, {751756, 757984}, {722156, 727311}, {157894, 165015}, {388628, 395987}, {87922, 93394}, {99736, 104300}, {386962, 391925}, {817392, 822398}, {573676, 579054}, {958545, 965823}, {760630, 767012}, {47716, 55010}, {819304, 824150}, {939397, 945084}, {27147, 31472}, {734751, 740369}, {824177, 831039}, {462418, 468241}, {999169, 1000000}, {631229, 638798}, {178868, 184101}, {881428, 886184}, {357051, 362439}, {65730, 70520}, {316297, 320865}, {502363, 507747}, {908390, 915980}, {55164, 62627}, {920502, 924630}, {416516, 423231}, {158147, 164501}, {60896, 67768}, {545801, 550655}, {758194, 763809}, {868631, 873743}, {388190, 395585}, {829057, 834691}, {891957, 898366}, {144291, 148907}, {811839, 817265}, {212628, 220006}, {937768, 943482}, {165888, 173285}, {102520, 110293}, {498875, 506701}, {229638, 237615}, {339428, 344103}, {896170, 902528}, {782453, 788487}, {224644, 230335}, {792736, 800140}, {355587, 362116}, {511394, 515478}, {520866, 528166}, {685561, 690929}, {817792, 825125}, {478986, 485486}, {324337, 329606}, {619633, 626543}, {783436, 788929}, {281221, 288831}, {155103, 160519}, {510535, 516292}, {946943, 952985}, {212320, 216351}, {710126, 715835}, {349174, 354471}, {467579, 472102}, {33985, 40764}, {68877, 73856}, {39095, 46150}, {93790, 98022}, {650688, 657917}, {569423, 576817}, {6087, 12420}, {228660, 233700}, {302030, 308240}, {892961, 898613}, {658620, 665404}, {576420, 584258}, {816974, 823846}, {424002, 431258}, {196033, 201473}, {310974, 316698}, {187536, 193427}, {610333, 616627}, {415475, 422492}, {190431, 197702}, {753011, 759348}, {788364, 793916}, {723086, 729624}, {602554, 609160}, {235639, 243483}, {243014, 248726}, {978785, 985431}, {303645, 309458}, {852422, 858394}, {188816, 194088}, {227327, 234510}, {386875, 392977}, {668959, 673835}, {70255, 78236}, {100599, 107797}, {296780, 303721}, {381444, 386088}, {818459, 823023}, {84092, 92028}, {232967, 239103}, {908839, 913118}, {842141, 846161}, {407206, 414594}, {799627, 803854}, {65271, 72279}, {157415, 163120}, {346297, 353935}, {774254, 779332}, {53933, 58401}, {853601, 859270}, {878633, 886308}, {120292, 128137}, {173894, 179178}, {536421, 543802}, {304127, 310077}, {646151, 653806}, {114078, 121382}, {374112, 379934}, {751894, 758156}, {305785, 311785}, {183709, 189951}, {731909, 739278}, {280670, 285911}, {461781, 469183}, {11818, 18297}, {200705, 206149}, {403105, 411051}, {485635, 492525}, {627422, 631787}, {83770, 89384}, {654172, 661180}, {728570, 736449}, {267506, 273348}, {167996, 175218}, {603004, 607572}, {262361, 268733}, {297477, 303990}, {994024, 998982}, {907719, 915256}, {701776, 707994}, {919336, 926804}, {199760, 204880}, {931374, 937385}, {785675, 791738}, {305029, 310323}, {540640, 545177}, {885698, 892609}, {216566, 222994}, {636789, 642075}, {572427, 579915}, {486937, 493842}, {903564, 911527}, {748637, 752891}, {306717, 312103}, {226651, 232558}, {981664, 987362}, {98690, 103958}, {439863, 445675}, {833090, 841066}, {92003, 99792}, {302560, 306622}, {549130, 553578}, {143414, 149510}, {665821, 671696}, {403494, 408705}, {586789, 594620}, {408960, 415095}, {684451, 688955}, {324196, 330374}, {71396, 79280}, {178956, 186346}, {635423, 640531}, {698849, 704961}, {545684, 552897}, {182699, 189591}, {71155, 76314}, {626013, 631548}, {244407, 249688}, {442409, 447836}, {810610, 815623}, {366564, 370932}, {42322, 49581}, {176177, 180576}, {735306, 741796}, {938206, 945700}, {164657, 168901}, {461393, 467649}, {475312, 480860}, {954094, 958344}, {287905, 292772}, {755171, 762639}, {652487, 657766}, {395603, 402687}, {164767, 170576}, {659999, 665494}, {150618, 155436}, {925576, 931033}, {669571, 675009}, {554993, 559150}, {796063, 800182}, {514372, 520578}, {494488, 498903}, {575878, 581789}, {55455, 59598}, {581470, 587006}, {499961, 505337}, {82592, 87650}, {517531, 523768}, {931002, 935655}, {298513, 305127}, {284966, 290389}, {97527, 102447}, {145309, 152758}, {854097, 860291}, {555335, 560278}, {563904, 571251}, {450451, 457285}, {562538, 566728}, {140856, 145766}, {233456, 238139}, {814667, 821926}, {699913, 707275}, {770272, 778056}, {558941, 565332}, {671219, 676558}, {590707, 598201}, {740186, 747923}, {307899, 314175}, {527742, 532029}, {831660, 839399}, {82609, 88703}, {155211, 159670}, {818226, 824562}, {55556, 62254}, {484959, 492251}, {969000, 973015}, {174626, 181296}, {213308, 220170}, {118249, 122902}, {610981, 618819}, {378957, 383132}, {184124, 189996}, {980878, 988724}, {268227, 275541}, {75718, 81190}, {713194, 719913}, {972774, 978599}, {497514, 505349}, {323708, 328387}, {173648, 178756}, {178150, 182635}, {406868, 411945}, {528398, 535883}, {516486, 520782}, {978489, 986479}, {280033, 285887}, {320380, 327752}, {405569, 412659}, {70122, 77208}, {293867, 300743}, {639095, 645393}, {647212, 653141}, {315219, 319401}, {65049, 69646}, {623011, 630563}, {206346, 213972}, {155052, 160255}, {621989, 626785}, {931411, 936403}, {939063, 944154}, {938137, 944174}, {991425, 998392}, {992631, 997660}, {163945, 169915}, {712552, 718630}, {769895, 776797}, {612477, 619740}, {727239, 733762}, {477385, 483007}, {786759, 791141}, {510735, 516255}, {242905, 249638}, {425644, 432169}, {44359, 51937}, {422667, 428175}, {370546, 377887}, {206007, 212033}, {182988, 188973}, {892040, 896211}, {100961, 108406}, {745428, 752455}, {149385, 154249}, {209187, 214889}, {880375, 886983}, {981002, 985072}, {435041, 440168}, {460421, 466994}, {819362, 825521}, {947938, 955891}, {178321, 183091}, {896859, 901013}, {747247, 754819}, {408759, 415227}, {516160, 521441}, {219715, 226211}, {586133, 591597}, {923262, 927471}, {184321, 190582}, {960016, 965700}, {934667, 941124}, {380524, 385366}, {887752, 895276}, {702106, 706457}, {993733, 999223}, {994551, 1000000}, {5263, 10527}, {917580, 925205}, {218814, 224649}, {236719, 241525}, {322138, 326322}, {152559, 160345}, {558251, 565567}, {483857, 488577}, {377630, 381984}, {60724, 66044}, {564395, 569819}, {782697, 788807}, {305804, 313425}, {484215, 491236}, {711431, 716460}, {684765, 689972}, {907527, 911773}, {668896, 673630}, {934093, 940291}, {91452, 97734}, {677091, 682544}, {464541, 470419}, {391236, 395812}, {710563, 715707}, {517169, 523540}, {640942, 647721}, {513245, 519918}, {83436, 90187}, {689178, 693227}, {566462, 570922}, {665344, 669725}, {473558, 479140}, {900390, 905247}, {17541, 24470}, {464370, 471934}, {36252, 42743}, {243540, 249989}, {343812, 350728}, {182537, 187456}, {990054, 996989}, {804787, 811045}, {134306, 139937}, {657727, 663576}, {23137, 28052}, {197453, 204428}, {632685, 638892}, {467508, 473300}, {750224, 754870}, {857948, 865327}, {549511, 554898}, {355573, 362514}, {99754, 104717}, {5783, 10685}, {934551, 940779}, {91344, 96978}, {872789, 877254}, {888963, 894081}, {56411, 64225}, {681810, 688404}, {619272, 625283}, {405653, 410268}, {553247, 558704}, {19683, 26683}, {143979, 149451}, {144496, 149765}, {923750, 929449}, {779193, 785791}, {923364, 928130}, {284294, 290472}, {506634, 511720}, {868212, 874590}, {530110, 536453}, {901012, 905457}, {822328, 829750}, {26748, 32244}, {239772, 247112}, {338469, 343113}, {265480, 273421}, {482106, 487835}, {890122, 896253}, {527366, 534091}, {548319, 552359}, {362368, 366486}, {953982, 959437}, {242019, 250002}, {893725, 899464}, {821509, 826576}, {619787, 626615}, {18692, 25942}, {267004, 274193}, {95341, 102834}, {29035, 36129}, {653676, 658033}, {38111, 43394}, {471336, 475573}, {703726, 708986}, {104552, 110495}, {147373, 154110}, {57835, 63668}, {159438, 167299}, {396753, 402788}, {203469, 210513}, {738252, 744187}, {732541, 738769}, {560999, 568666}, {906270, 911760}, {606208, 612727}, {914302, 921041}, {445192, 449886}, {318827, 325364}, {825535, 830947}, {662233, 668893}, {978796, 986627}, {472500, 479213}, {602938, 608977}, {607370, 614276}, {218434, 223290}, {374938, 380076}, {91350, 98140}, {272799, 278104}, {524640, 529765}, {528641, 532803}, {109651, 113792}, {99559, 104470}, {453700, 458230}, {989039, 993526}, {418556, 422570}, {749892, 757432}, {413839, 420187}, {95293, 99368}, {848470, 856300}, {225752, 232880}, {117770, 124936}, {951114, 955418}, {417819, 422148}, {281759, 286012}, {664795, 669438}, {677337, 684792}, {303849, 309060}, {673057, 679249}
        };

//        testMyCalendar(bookings);
        testMyCalendarWithTreeMap(bookings);
    }
}