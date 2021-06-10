package com.android.commonlibrary.util;

import android.content.Context;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.commonlibrary.R;
import com.android.commonlibrary.dialog_fragment.AgreementDialog;
import com.android.commonlibrary.dialog_fragment.AppDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title:默认用户协议帮助类
 * description:
 * autor:pei
 * created on 2020/8/21
 */
public class AgreementDefaultHelper {

    public static final String TAG="AgreementDefaultHelper_tag";

    private static final String AGREEMENT_KEY="agreement_helper_startIndex";
    private static final String AGREEMENT_VALUE="agreement_helper_endIndex";

    //联系方式
    private static String LINK="暂无";//默认为暂无,若添加的话,以电话为例则是 Tel: 15927453658
    //协议
    private static String USER_AGREEMENT="《用户服务协议》";
    private static String PRIVACY_AGREEMENT="《隐私权政策》";

    //用户协议弹框内容
    private static String AGREEMENT_DIALOG_CONTENT="我们非常重视您的隐私保护和个人信息" +
            "保护。在您使用\""+TAG+"\"提供的服务前,请" +
            "务必认真阅读"+USER_AGREEMENT +
            "与"+PRIVACY_AGREEMENT+"全部条款,您点" +
            "击\"同意\"即表示您已阅读并同意以上协议的" +
            "全部内容,请点击\"同意\",开始使用我们的产品和服务.";

    //用户协议(开头)
    private static String USER_CONTENT_FIRST="\n一、总则\n\n" +
            "\t\t1.1 用户应当同意本协议的条款并按照页面上的提示完成全部的注册程序。用户在进行注册程序过程中勾选\"同意"+TAG+USER_AGREEMENT+"\"按钮即表示用户与"+TAG+"达成协议，完全接受本协议项下的全部条款。\n\n" +
            "\t\t1.2 用户注册成功后，"+TAG+"将给予每个用户一个用户账号及相应的密码，该用户账号和密码由用户负责保管；用户应当对以其用户账号进行的所有活动和事件负法律责任。\n\n" +
            "\t\t1.3 用户可以使用"+TAG+"各个频道单项服务，当用户使用"+TAG+"各单项服务时，用户的使用行为视为其对该单项服务的服务条款以及"+TAG+"在该单项服务中发出的各类公告的同意。\n\n" +
            "\t\t1.4 "+TAG+"会员服务协议以及各个频道单项服务条款和公告可由"+TAG+"公司随时更新，且无需另行通知。您在使用相关服务时,应关注并遵守其所适用的相关条款。\n" +
            "\t\t您在使用"+TAG+"提供的各项服务之前，应仔细阅读本服务协议。如您不同意本服务协议及/或随时对其的修改，您可以主动取消"+TAG+"提供的服务；您一旦使用"+TAG+"服务，即视为您已了解并完全同意本服务协议各项内容，包括"+TAG+"对服务协议随时所做的任何修改，并成为"+TAG+"说用户。\n\n" +
            "二、注册信息和隐私保护\n\n" +
            "\t\t2.1 "+TAG+"账号（即"+TAG+"用户ID）的所有权归"+TAG+"，用户完成注册申请手续后，获得"+TAG+"账号的使用权。用户应提供及时、详尽及准确的个人资料，并不断更新注册资料，符合及时、详尽准确的要求。所有原始键入的资料将引用为注册资料。如果因注册信息不真实而引起的问题，并对问题发生所带来的后果，"+TAG+"不负任何责任。\n\n" +
            "\t\t2.2 用户不应将其账号、密码转让或出借予他人使用。如用户发现其账号遭他人非法使用，应立即通知"+TAG+"。因黑客行为或用户的保管疏忽导致账号、密码遭他人非法使用，"+TAG+"不承担任何责任。\n\n" +
            "\t\t2.3 "+TAG+"不对外公开或向第三方提供单个用户的注册资料，除非：\n" +
            "\t\t\t(1) 事先获得用户的明确授权；\n" +
            "\t\t\t(2) 只有透露你的个人资料，才能提供你所要求的产品和服务；\n" +
            "\t\t\t(3) 根据有关的法律法规要求；\n" +
            "\t\t\t(4) 按照相关政府主管部门的要求；\n" +
            "\t\t\t(5) 为维护"+TAG+"的合法权益。\n\n" +
            "\t\t2.4 在你注册"+TAG+"账户，使用其他"+TAG+"产品或服务，访问"+TAG+"网页, 或参加促销和有奖游戏时，"+TAG+"会收集你的个人身份识别资料，并会将这些资料用于：改进为你提供的服务及网页内容。\n\n" +
            "三、使用规则\n\n" +
            "\t\t3.1 用户在使用"+TAG+"服务时，必须遵守中华人民共和国相关法律法规的规定，用户应同意将不会利用本服务进行任何违法或不正当的活动，包括但不限于下列行为∶\n" +
            "\t\t\t(1) 上载、展示、张贴、传播或以其它方式传送含有下列内容之一的信息： \n" +
            "\t\t\t\t<1 反对宪法所确定的基本原则的； \n" +
            "\t\t\t\t<2 危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的； \n" +
            "\t\t\t\t<3 损害国家荣誉和利益的；\n" +
            "\t\t\t\t<4 煽动民族仇恨、民族歧视、破坏民族团结的；\n" +
            "\t\t\t\t<5 破坏国家宗教政策，宣扬邪教和封建迷信的；\n" +
            "\t\t\t\t<6 散布谣言，扰乱社会秩序，破坏社会稳定的；\n" +
            "\t\t\t\t<7 散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；\n" +
            "\t\t\t\t<8 侮辱或者诽谤他人，侵害他人合法权利的；\n" +
            "\t\t\t\t<9 含有虚假、有害、胁迫、侵害他人隐私、骚扰、侵害、中伤、粗俗、猥亵、或其它道德上令人反感的内容；\n" +
            "\t\t\t\t<10 含有中国法律、法规、规章、条例以及任何具有法律效力之规范所限制或禁止的其它内容的；\n" +
            "\t\t\t(2) 不得为任何非法目的而使用网络服务系统；\n" +
            "\t\t\t(3) 不利用"+TAG+"服务从事以下活动：\n" +
            "\t\t\t\t<1 未经允许，进入计算机信息网络或者使用计算机信息网络资源的；\n" +
            "\t\t\t\t<2 未经允许，对计算机信息网络功能进行删除、修改或者增加的；\n" +
            "\t\t\t\t<3 未经允许，对进入计算机信息网络中存储、处理或者传输的数据和应用程序进行删除、修改或者增加的；\n" +
            "\t\t\t\t<4 故意制作、传播计算机病毒等破坏性程序的；\n" +
            "\t\t\t\t<5 其他危害计算机信息网络安全的行为。\n\n" +
            "\t\t3.2 用户违反本协议或相关的服务条款的规定，导致或产生的任何第三方主张的任何索赔、要求或损失，包括合理的律师费，您同意赔偿"+TAG+"与合作公司、关联公司，并使之免受损害。对此，"+TAG+"有权视用户的行为性质，采取包括但不限于删除用户发布信息内容、暂停使用许可、终止服务、限制使用、回收"+TAG+"账号、追究法律责任等措施。对恶意注册"+TAG+"账号或利用"+TAG+"账号进行违法活动、捣乱、骚扰、欺骗、其他用户以及其他违反本协议的行为，"+TAG+"有权回收其账号。同时，"+TAG+"公司会视司法部门的要求，协助调查。\n\n" +
            "\t\t3.3 用户不得对本服务任何部分或本服务之使用或获得，进行复制、拷贝、出售、转售或用于任何其它商业目的。\n\n" +
            "\t\t3.4 用户须对自己在使用"+TAG+"服务过程中的行为承担法律责任。用户承担法律责任的形式包括但不限于：对受到侵害者进行赔偿，以及在"+TAG+"公司首先承担了因用户行为导致的行政处罚或侵权损害赔偿责任后，用户应给予"+TAG+"公司等额的赔偿。\n\n" +
            "四、服务内容\n\n" +
            "\t\t4.1 "+TAG+"网络服务的具体内容由"+TAG+"根据实际情况提供。\n\n" +
            "\t\t4.2 除非本服务协议另有其它明示规定，"+TAG+"所推出的新产品、新功能、新服务，均受到本服务协议之规范。\n\n" +
            "\t\t4.3 为使用本服务，您必须能够自行经有法律资格对您提供互联网接入服务的第三方，进入国际互联网，并应自行支付相关服务费用。此外，您必须自行配备及负责与国际联网连线所需之一切必要装备，包括计算机、数据机或其它存取装置。\n\n" +
            "\t\t4.4 鉴于网络服务的特殊性，用户同意"+TAG+"有权不经事先通知，随时变更、中断或终止部分或全部的网络服务（包括收费网络服务）。"+TAG+"不担保网络服务不会中断，对网络服务的及时性、安全性、准确性也都不作担保。\n\n" +
            "\t\t4.5 "+TAG+"需要定期或不定期地对提供网络服务的平台或相关的设备进行检修或者维护，如因此类情况而造成网络服务（包括收费网络服务）在合理时间内的中断，"+TAG+"无需为此承担任何责任。"+TAG+"保留不经事先通知为维修保养、升级或其它目的暂停本服务任何部分的权利。\n\n" +
            "\t\t4.6 本服务或第三人可提供与其它国际互联网上之或资源之链接。由于"+TAG+"无法控制这些及资源，您了解并同意，此类或资源是否可供利用，"+TAG+"不予负责，存在或源于此类或资源之任何内容、广告、产品或其它资料，"+TAG+"亦不予保证或负责。因使用或依赖任何此类或资源发布的或经由此类或资源获得的任何内容、商品或服务所产生的任何损害或损失，"+TAG+"不承担任何责任。\n\n" +
            "\t\t4.7 用户明确同意其使用"+TAG+"网络服务所存在的风险将完全由其自己承担。用户理解并接受下载或通过"+TAG+"服务取得的任何信息资料取决于用户自己，并由其承担系统受损、资料丢失以及其它任何风险。"+TAG+"对在服务网上得到的任何商品购物服务、交易进程、招聘信息，都不作担保。\n\n" +
            "\t\t4.8 6个月未登录的账号，"+TAG+"保留关闭的权利。\n\n" +
            "\t\t4.9 "+TAG+"有权于任何时间暂时或永久修改或终止本服务（或其任何部分），而无论其通知与否，"+TAG+"对用户和任何第三人均无需承担任何责任。\n\n" +
            "\t\t4.10 终止服务\n" +
            "\t\t您同意"+TAG+"得基于其自行之考虑，因任何理由，包含但不限于长时间未使用，或"+TAG+"认为您已经违反本服务协议的文字及精神，终止您的密码、账号或本服务之使用（或服务之任何部分），并将您在本服务内任何内容加以移除并删除。您同意依本服务协议任何规定提供之本服务，无需进行事先通知即可中断或终止，您承认并同意，"+TAG+"可立即关闭或删除您的账号及您账号中所有相关信息及文件，及/或禁止继续使用前述文件或本服务。此外，您同意若本服务之使用被中断或终止或您的账号及相关信息和文件被关闭或删除，"+TAG+"对您或任何第三人均不承担任何责任。\n\n" +
            "五、知识产权和其他合法权益（包括但不限于名誉权、商誉权）\n\n" +
            "\t\t5.1 用户专属权利\n" +
            "\t\t"+TAG+"尊重他人知识产权和合法权益，呼吁用户也要同样尊重知识产权和他人合法权益。若您认为您的知识产权或其他合法权益被侵犯，请按照以下说明向"+TAG+"提供资料∶\n" +
            "\t\t请注意：如果权利通知的陈述失实，权利通知提交者将承担对由此造成的全部法律责任（包括但不限于赔偿各种费用及律师费）。如果上述个人或单位不确定网络上可获取的资料是否侵犯了其知识产权和其他合法权益，"+TAG+"建议该个人或单位首先咨询专业人士。\n" +
            "\t\t为了"+TAG+"有效处理上述个人或单位的权利通知，请使用以下格式（包括各条款的序号）：\n" +
            "\t\t\t(1) 权利人对涉嫌侵权内容拥有知识产权或其他合法权益和/或依法可以行使知识产权或其他合法权益的权属证明；\n" +
            "\t\t\t(2) 请充分、明确地描述被侵犯了知识产权或其他合法权益的情况并请提供涉嫌侵权的第三方网址（如果有）。\n" +
            "\t\t\t(3) 请指明涉嫌侵权网页的哪些内容侵犯了第2项中列明的权利。\n" +
            "\t\t\t(4) 请提供权利人具体的联络信息，包括姓名、身份证或护照复印件（对自然人）、单位登记证明复印件（对单位）、通信地址、电话号码、传真和电子邮件。\n" +
            "\t\t\t(5) 请提供涉嫌侵权内容在信息网络上的位置（如指明您举报的含有侵权内容的出处，即：指网页地址或网页内的位置）以便我们与您举报的含有侵权内容的网页的所有权人/管理人联系。\n" +
            "\t\t\t(6) 请在权利通知中加入如下关于通知内容真实性的声明： \"我保证，本通知中所述信息是充分、真实、准确的，如果本权利通知内容不完全属实，本人将承担由此产生的一切法律责任。\n\n" +
            "\t\t5.2 对于用户通过"+TAG+"服务（包括但不限于阅读网站、社区、论坛、客户端等）上传到"+TAG+"上可公开获取区域的任何内容，用户同意"+TAG+"在全世界范围内具有免费的、永久性的、不可撤销的、非独家的和完全再许可的权利和许可，以使用、复制、修改、改编、出版、翻译、据以创作衍生作品、传播、表演和展示此等内容（整体或部分），和/或将此等内容编入当前已知的或以后开发的其他任何形式的作品、媒体或技术中。\n\n" +
            "\t\t5.3 "+TAG+"拥有本内所有资料的版权。任何被授权的浏览、复制、打印和传播属于本内的资料必须符合以下条件：\n" +
            "\t\t\t(1) 所有的资料和图象均以获得信息为目的；\n" +
            "\t\t\t(2) 所有的资料和图象均不得用于商业目的；\n" +
            "\t\t\t(3) 所有的资料、图象及其任何部分都必须包括此版权声明；\n" +
            "\t\t\t(4) "+TAG+"产品、技术与所有程序均属于"+TAG+"知识产权，在此并未授权。\n" +
            "\t\t\t(5) \""+TAG+"\"及相关图形等为"+TAG+"的注册商标。\n\n" +
            "\t\t未经"+TAG+"许可，任何人不得擅自（包括但不限于：以非法的方式复制、传播、展示、镜像、上载、下载）使用。否则，"+TAG+"将依法追究法律责任。\n\n" +
            "六、青少年用户特别提示\n\n" +
            "\t\t青少年用户必须遵守全国青少年网络文明公约：\n" +
            "\t\t\t(1) 要善于网上学习，不浏览不良信息；\n" +
            "\t\t\t(2) 要诚实友好交流，不侮辱欺诈他人；\n" +
            "\t\t\t(3) 要增强自护意识，不随意约会网友；\n" +
            "\t\t\t(4) 要维护网络安全，不破坏网络秩序；\n" +
            "\t\t\t(5) 要有益身心健康，不沉溺虚拟时空。\n\n" +
            "七、其他\n\n" +
            "\t\t7.1 本协议的订立、执行和解释及争议的解决均应适用中华人民共和国法律。\n\n" +
            "\t\t7.2 如双方就本协议内容或其执行发生任何争议，双方应尽量友好协商解决；协商不成时，任何一方均可向"+TAG+"所在地的人民法院提起诉讼。\n\n" +
            "\t\t7.3 "+TAG+"未行使或执行本服务协议任何权利或规定，不构成对前述权利或权利之放弃。\n\n" +
            "\t\t7.4 如本协议中的任何条款无论因何种原因完全或部分无效或不具有执行力，本协议的其余条款仍应有效并且有约束力。\n\n";

    //用户协议(结尾)
    private static String USER_CONTENT_LAST="\t\t请您在发现任何违反本服务协议以及其他任何单项服务的服务条款、"+TAG+"各类公告之情形时，通知"+TAG+"。您可以通过如下联络方式同"+TAG+"联系:\n\n"+
            "\t\t"+LINK+"\n\n";

    //隐私协议
    public static String PRIVACY_CONTENT="\n\t\t"+TAG+"尊重并保护所有使用服务用户的个人隐私权。为了给您提供更准确、更有个性化的服务，"+TAG+"会按照本隐私权政策的规定使用和披露您的个人信息。但"+TAG+"将以高度的勤勉、审慎义务对待这些信息。除本隐私权政策另有规定外，在未征得您事先许可的情况下，"+TAG+"不会将这些信息对外披露或向第三方提供。"+TAG+"会不时更新本隐私权政策。您在同意"+TAG+"服务使用协议之时，即视为您已经同意本隐私权政策全部内容。本隐私权政策属于"+TAG+"服务使用协议不可分割的一部分。\n\n" +
            "\t\t1. 适用范围\n" +
            "\t\t\t(a) 在您注册"+TAG+"帐号时，您根据"+TAG+"要求提供的个人注册信息；\n" +
            "\t\t\t(b) 在您使用"+TAG+"网络服务，或访问"+TAG+"平台网页时，"+TAG+"自动接收并记录的您的浏览器和计算机上的信息，包括但不限于您的IP地址、浏览器的类型、使用的语言、访问日期和时间、软硬件特征信息及您需求的网页记录等数据；\n" +
            "\t\t\t(c) "+TAG+"通过合法途径从商业伙伴处取得的用户个人数据。\n" +
            "\t\t您了解并同意，以下信息不适用本隐私权政策：\n" +
            "\t\t\t(a) 您在使用"+TAG+"平台提供的搜索服务时输入的关键字信息；\n" +
            "\t\t\t(b) "+TAG+"收集到的您在"+TAG+"发布的有关信息数据，包括但不限于参与活动、成交信息及评价详情；\n" +
            "\t\t\t(c) 违反法律规定或违反"+TAG+"规则行为及"+TAG+"已对您采取的措施。\n\n" +
            "\t\t2. 信息使用\n" +
            "\t\t\t(a) "+TAG+"不会向任何无关第三方提供、出售、出租、分享或交易您的个人信息，除非事先得到您的许可，或该第三方和"+TAG+"（含"+TAG+"关联公司）单独或共同为您提供服务，且在该服务结束后，其将被禁止访问包括其以前能够访问的所有这些资料。\n" +
            "\t\t\t(b) "+TAG+"亦不允许任何第三方以任何手段收集、编辑、出售或者无偿传播您的个人信息。任何"+TAG+"平台用户如从事上述活动，一经发现，"+TAG+"有权立即终止与该用户的服务协议。\n" +
            "\t\t\t(c) 为服务用户的目的，"+TAG+"可能通过使用您的个人信息，向您提供您感兴趣的信息，包括但不限于向您发出产品和服务信息，或者与"+TAG+"合作伙伴共享信息以便他们向您发送有关其产品和服务的信息（后者需要您的事先同意）。\n\n" +
            "\t\t3. 信息披露\n" +
            "\t\t在如下情况下，"+TAG+"将依据您的个人意愿或法律的规定全部或部分的披露您的个人信息：\n" +
            "\t\t\t(a) 经您事先同意，向第三方披露；\n" +
            "\t\t\t(b) 为提供您所要求的产品和服务，而必须和第三方分享您的个人信息；\n" +
            "\t\t\t(c) 根据法律的有关规定，或者行政或司法机构的要求，向第三方或者行政、司法机构披露；\n" +
            "\t\t\t(d) 如您出现违反中国有关法律、法规或者"+TAG+"服务协议或相关规则的情况，需要向第三方披露；\n" +
            "\t\t\t(e) 如您是适格的知识产权投诉人并已提起投诉，应被投诉人要求，向被投诉人披露，以便双方处理可能的权利纠纷；\n" +
            "\t\t\t(f) 在"+TAG+"平台上创建的某一交易中，如交易任何一方履行或部分履行了交易义务并提出信息披露请求的，"+TAG+"有权决定向该用户提供其交易对方的联络方式等必要信息，以促成交易的完成或纠纷的解决。\n" +
            "\t\t\t(g) 其它"+TAG+"根据法律、法规或者网站政策认为合适的披露。\n\n" +
            "\t\t4. 信息存储和交换\n" +
            "\t\t"+TAG+"收集的有关您的信息和资料将保存在"+TAG+"及（或）其关联公司的服务器上，这些信息和资料可能传送至您所在国家、地区或"+TAG+"收集信息和资料所在地的境外并在境外被访问、存储和展示。\n\n" +
            "\t\t5. Cookie的使用\n" +
            "\t\t\t(a) 在您未拒绝接受cookies的情况下，"+TAG+"会在您的计算机上设定或取用cookies" +
            "，以便您能登录或使用依赖于cookies的"+TAG+"平台服务或功能。"+TAG+"使用cookies可为您提供更加周到的个性化服务，包括推广服务。\n" +
            "\t\t\t(b) 您有权选择接受或拒绝接受cookies。您可以通过修改浏览器设置的方式拒绝接受cookies。但如果您选择拒绝接受cookies，则您可能无法登录或使用依赖于cookies的"+TAG+"网络服务或功能。\n" +
            "\t\t\t(c) 通过"+TAG+"所设cookies所取得的有关信息，将适用本政策。\n\n" +
            "\t\t6. 信息安全\n" +
            "\t\t\t(a) "+TAG+"帐号均有安全保护功能，请妥善保管您的用户名及密码信息。"+TAG+"将通过对用户密码进行加密等安全措施确保您的信息不丢失，不被滥用和变造。尽管有前述安全措施，但同时也请您注意在信息网络上不存在“完善的安全措施”。\n" +
            "\t\t\t(b) 在使用"+TAG+"网络服务进行网上交易时，您不可避免的要向交易对方或潜在的交易对方披露自己的个人信息，如联络方式或者邮政地址。请您妥善保护自己的个人信息，仅在必要的情形下向他人提供。如您发现自己的个人信息泄密，尤其是"+TAG+"用户名及密码发生泄露，请您立即联络"+TAG+"客服，以便"+TAG+"采取相应措施。";

    /**获取用户协议弹框内容**/
    public static String getAgreementDialogContent(String appName){
        String content=null;
        if(StringUtil.isNotEmpty(appName)){
            content=AGREEMENT_DIALOG_CONTENT.replace(TAG,appName);
        }
        return content;
    }

    /***
     * 用户协议弹框的设置(包括弹框内容，要点击的文字的颜色和设置点击事件)
     *
     * @param textView 设置用户协议弹框内容的textView
     * @param appName app名称
     * @param textColor 需要点击字段文字的颜色
     * @param link 联系方式。设置为null的话表示"暂无"
     *             添加电话的示例  Tel: 15927453658
     *             添加qq的示例  QQ: 67030030
     * @param userListener  《用户协议》点击事件
     * @param privacyListener 《隐私协议》点击事件
     */
    public static void clickAgreement(TextView textView, String appName, int textColor, String link,View.OnClickListener userListener, View.OnClickListener privacyListener){
        if(StringUtil.isEmpty(appName)||textView==null||textColor==0){
            return;
        }
        //联系方式
        if(StringUtil.isNotEmpty(link)){
            LINK=link;
        }
        SpannableString sp=null;
        //用户协议设置
        if(userListener!=null) {
            sp = SpannableStringUtil.setClickText(getAgreementDialogContent(appName), USER_AGREEMENT, textColor, false, userListener);
        }
        //隐私协议设置
        if(privacyListener!=null) {
            sp = SpannableStringUtil.setClickText(sp, PRIVACY_AGREEMENT, textColor, false, privacyListener);
        }
        if(sp!=null) {
            textView.setText(sp);
            //必须设置才能响应点击事件
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    /***
     * 获取用户协议
     *
     * @param appName: App名称
     * @param append: 需要补充的用户协议信息,如无需补充,则此参数为null
     * @return
     */
    public static String getUserContent(String appName,String append){
        String content=null;
        if(StringUtil.isNotEmpty(appName)){
            String first=USER_CONTENT_FIRST.replace(TAG,appName);
            String last=USER_CONTENT_LAST.replace(TAG,appName);

            if(StringUtil.isEmpty(append)){
                content=first+last;
            }else{
                content=first+"\t\t"+append+"\n\n"+last;
            }
        }else{
            content=append;
        }
        return content;
    }

    /***
     * 获取隐私协议
     *
     * @param appName App名称
     * @param append 需要补充的隐私协议信息,如无需补充,则此参数为null
     * @return
     */
    public static String getPrivacyContent(String appName,String append){
        String content=null;
        if(StringUtil.isNotEmpty(appName)){
            content=PRIVACY_CONTENT.replace(TAG,appName);
            if(StringUtil.isEmpty(append)){
                content=content+"\n\n";
            }else{
                content=content+"\n\t\t"+append+"\n\n";
            }
        }
        return content;
    }

    /***
     * 弹出默认dialog
     *
     * @param context 上下文
     * @param appName app名称
     * @param scaleWidth dialog宽度尺寸比,当设置为 0d时,会默认dialog宽度为屏幕宽的 0.7d
     *                   (注:高度是自适应的,WRAP_CONTENT)
     * @param agreementColor "用户协议","隐私协议"文字颜色, R.color.color_red
     * @param link 联系方式 默认为"暂无",若添加的话,以电话为例则是 Tel: 15927453658
     * @param link confirmBackground 确认按钮背景,如 R.drawable.c,R.color.red
     * @param link cancelBackground  取消按钮背景,如 R.drawable.c,R.color.red
     * @param userListener 跳转用户协议界面的监听
     * @param privacyListener 跳转隐私协议界面的监听
     * @param cancelListener 取消按钮监听,一般执行退出app的操作
     * @param confirmListener 确定按钮的操作,一般处理进入app的流程
     */
    public static void showDefaultAgreementDialog(Context context,String appName,
                                                  double scaleWidth,
                                                  int agreementColor,String link,
                                                  int confirmBackground,int cancelBackground,
                                                  View.OnClickListener userListener,
                                                  View.OnClickListener privacyListener,
                                                  View.OnClickListener cancelListener,
                                                  View.OnClickListener confirmListener){
        //用户协议
        AgreementDialog agreementDialog = (AgreementDialog) AgreementDialog.createFragment(AgreementDialog.class, context, new AppDialogFragment.OnCreateFragmentListener() {
            @Override
            public Fragment createFragment() {
                return new AgreementDialog();
            }
        });
        agreementDialog.setDialogSize(scaleWidth) //设置dialog显示尺寸
                .setUserListener(userListener)//跳转用户协议界面的监听
                .setPrivacyListener(privacyListener)//跳转隐私协议界面的监听
                //对tvContent内部数据做特殊处理的监听
                .setOnActionListener(new AgreementDialog.OnActionListener() {
                    @Override
                    public void action(TextView tvContent, View.OnClickListener userListener, View.OnClickListener privacyListener) {
                        AgreementDefaultHelper.clickAgreement(tvContent,appName, agreementColor,link,userListener,privacyListener);
                    }
                })
                //取消按钮监听,一般执行退出app的操作
                .setCancelBtn(cancelListener)
                //确定按钮的操作,一般处理进入app的流程
                .setConfirmBtn(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmListener.onClick(v);
                        //关闭当前dialog
                        agreementDialog.dismiss();
                    }
                })
                .setCancelmBackground(cancelBackground)//取消按钮背景
                .setConfirmBackground(confirmBackground)//确认按钮背景
                .setUIShadow(true)//默认弹出diaolog时,界面有遮罩
                        .setRidShadow(true)//当dialog设置圆角背景的时候，会留下边角阴影,设置true的时候，可去除边角,默认为false，即有边角阴影
                        .setCancel(false)//返回键是否关闭dialog
                        .setCancelOnTouchOutside(false)//屏幕外点击是否关闭
                        .showDialog(((AppCompatActivity)context).getSupportFragmentManager());//显示dialog
    }

    /***
     * 修改一个字符串中所有以startFlag开头，以endFlag结束的字段颜色为colorId
     *
     * @param message：操作源
     * @param startFlag：开头tag
     * @param endFlag：结尾 tag
     * @param colorId：要变色的color,如 R.color.red
     * @return
     */
    public static SpannableString translate(String message, String startFlag, String endFlag,int colorId){
        SpannableString sp = null;
        LogUtil.i("======1====message="+message);
        if(StringUtil.isNotEmpty(message)){
            List<Map<String,Integer>>indexList = checkList(message,startFlag,endFlag);
            LogUtil.i("======2====indexList="+indexList);
            if(!indexList.isEmpty()){
                sp = new SpannableString(message);
                LogUtil.i("======3====sp="+sp);
                for(Map<String,Integer>map:indexList){
                    int startIndex = map.get(AGREEMENT_KEY);
                    int endIndex = map.get(AGREEMENT_VALUE);
                    LogUtil.i("======map====key="+startIndex+"  value="+endIndex);
                    sp = SpannableStringUtil.setTextFrontColor(sp,startIndex,endIndex+1, colorId);
                }
            }
        }
        return sp;
    }

    private static List<Map<String,Integer>> checkList(String message, String startFlag, String endFlag){
        List<Map<String,Integer>>list  = new ArrayList<>();
        return checkString(message,startFlag,endFlag,list);
    }

    private static List<Map<String,Integer>> checkString(String message, String startFlag, String endFlag,List<Map<String,Integer>>list){
        String temp=null;
        if(StringUtil.isNotEmpty(message)){
            if(message.contains(startFlag)&&message.contains(endFlag)){
                int startIndex=message.indexOf(startFlag);
                int endIndex=message.indexOf(endFlag);
                if(endIndex>startIndex){
                    LogUtil.i("====startIndex="+startIndex+"  endIndex="+endIndex);
                    if(startIndex<endIndex-1){
                        LogUtil.i("======合法输出======");
                        Map<String,Integer>map = new HashMap<>();
                        map.put(AGREEMENT_KEY,startIndex);
                        map.put(AGREEMENT_VALUE,endIndex);
                        if(!list.contains(map)){
                            list.add(map);
                        }
                    }else{
                        LogUtil.i("======非法输出======");
                    }
                    temp = StringUtil.replaceByTarget(message,startFlag,"#");
                    temp = StringUtil.replaceByTarget(temp,endFlag,"#");
                    return checkString(temp,startFlag,endFlag,list);
                }else if(endIndex<startIndex){
                    temp=StringUtil.replaceByTarget(message,endFlag,"#");
                    return checkString(temp,startFlag,endFlag,list);
                }
            }
        }
        LogUtil.i("====结束===list="+list);
        return list;
    }

}

//=======================调用示例=================================
//        AgreementDefaultHelper.showDefaultAgreementDialog(mContext, "测试app",
//                0d,
//                R.color.red, null,
//                R.color.colorAccent,R.color.green,
//                //跳转用户协议界面的监听
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        LogUtil.i("====跳转用户协议=======");
//                    }
//                },
//                //跳转隐私协议界面的监听
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        LogUtil.i("====跳转隐私协议=======");
//                    }
//                },
//                //取消按钮监听,一般执行退出app的操作
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        LogUtil.i("====退出app=======");
//                    }
//                },
//                //确定按钮的操作,一般处理进入app的流程
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        LogUtil.i("====进入app=======");
//                    }
//                });
