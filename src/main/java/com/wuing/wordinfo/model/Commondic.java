package com.wuing.wordinfo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

@Data
@TableName(value = "dictionariesplus.commondic")
public class Commondic implements Serializable {
    /**
     * 词汇ID
     */
    @TableId(value = "vc_id", type = IdType.AUTO)
    private Integer vcId;

    /**
     * originalId
     */
    @TableField(value = "original_id")
    private Integer originalId;

    /**
     * 词汇
     */
    @TableField(value = "vc_vocabulary")
    private String vcVocabulary;

    /**
     * 词汇简写
     */
    @TableField(value = "vc_sw")
    private String vcSw;

    /**
     * 时态复数等变换，使用 "/" 分割不同项目，见后面表格
     */
    @TableField(value = "exchange")
    private String exchange;

    /**
     * 字符串标签：zk/中考，gk/高考，cet4/四级 等等标签，空格分割
     */
    @TableField(value = "tag")
    private String tag;

    /**
     * 英标
     */
    @TableField(value = "vc_phonetic_uk")
    private String vcPhoneticUk;

    /**
     * 美标
     */
    @TableField(value = "vc_phonetic_us")
    private String vcPhoneticUs;

    /**
     * 词频
     */
    @TableField(value = "vc_frequency")
    private String vcFrequency;

    /**
     * 困难程度
     */
    @TableField(value = "vc_difficulty")
    private String vcDifficulty;

    /**
     * 认知率
     */
    @TableField(value = "vc_acknowledge_rate")
    private String vcAcknowledgeRate;

    /**
     * 学习人数
     */
    @TableField(value = "vc_study_user_count")
    private String vcStudyUserCount;

    /**
     * 词语位置，用 "/" 分割不同位置
     */
    @TableField(value = "pos")
    private String pos;

    /**
     * 柯林斯星级
     */
    @TableField(value = "collins")
    private Integer collins;

    /**
     * 是否是牛津三千核心词汇
     */
    @TableField(value = "oxford")
    private Integer oxford;

    /**
     * 英国国家语料库词频顺序
     */
    @TableField(value = "bnc")
    private Integer bnc;

    /**
     * 当代语料库词频顺序
     */
    @TableField(value = "frq")
    private Integer frq;

    /**
     * 英音
     */
    @TableField(value = "vc_speech_uk")
    private String vcSpeechUk;

    /**
     * 美音
     */
    @TableField(value = "vc_speech_us")
    private String vcSpeechUs;

    /**
     * 0:单词，1:短语
     */
    @TableField(value = "isword")
    private Integer isword;

    /**
     * 翻译
     */
    @TableField(value = "vc_translation")
    private String vcTranslation;

    /**
     * 网络翻译
     */
    @TableField(value = "vc_translation_net")
    private String vcTranslationNet;

    private static final long serialVersionUID = 1L;

    public static final String COL_VC_ID = "vc_id";

    public static final String COL_ORIGINAL_ID = "original_id";

    public static final String COL_VC_VOCABULARY = "vc_vocabulary";

    public static final String COL_VC_SW = "vc_sw";

    public static final String COL_EXCHANGE = "exchange";

    public static final String COL_TAG = "tag";

    public static final String COL_VC_PHONETIC_UK = "vc_phonetic_uk";

    public static final String COL_VC_PHONETIC_US = "vc_phonetic_us";

    public static final String COL_VC_FREQUENCY = "vc_frequency";

    public static final String COL_VC_DIFFICULTY = "vc_difficulty";

    public static final String COL_VC_ACKNOWLEDGE_RATE = "vc_acknowledge_rate";

    public static final String COL_VC_STUDY_USER_COUNT = "vc_study_user_count";

    public static final String COL_POS = "pos";

    public static final String COL_COLLINS = "collins";

    public static final String COL_OXFORD = "oxford";

    public static final String COL_BNC = "bnc";

    public static final String COL_FRQ = "frq";

    public static final String COL_VC_SPEECH_UK = "vc_speech_uk";

    public static final String COL_VC_SPEECH_US = "vc_speech_us";

    public static final String COL_ISWORD = "isword";

    public static final String COL_VC_TRANSLATION = "vc_translation";

    public static final String COL_VC_TRANSLATION_NET = "vc_translation_net";
}