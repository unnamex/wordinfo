package com.wuing.wordinfo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName(value = "dictionariesplus.voc_tb")
public class VocTb implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * vcId
     */
    @TableField(value = "vc_id")
    private String vcId;

    /**
     * originalId
     */
    @TableField(value = "original_id")
    private Integer originalId;

    /**
     * vcVocabulary
     */
    @TableField(value = "vc_vocabulary")
    private String vcVocabulary;

    /**
     * vcInitial
     */
    @TableField(value = "vc_initial")
    private String vcInitial;

    /**
     * vcFrequency
     */
    @TableField(value = "vc_frequency")
    private String vcFrequency;

    /**
     * vcDifficulty
     */
    @TableField(value = "vc_difficulty")
    private String vcDifficulty;

    /**
     * vcPhrase
     */
    @TableField(value = "vc_phrase")
    private String vcPhrase;

    /**
     * vcUpdatedTime
     */
    @TableField(value = "vc_updated_time")
    private LocalDateTime vcUpdatedTime;

    /**
     * vcStudyUserCount
     */
    @TableField(value = "vc_study_user_count")
    private String vcStudyUserCount;

    /**
     * vcAcknowledgeRate
     */
    @TableField(value = "vc_acknowledge_rate")
    private String vcAcknowledgeRate;

    /**
     * status
     */
    @TableField(value = "status")
    private String status;

    /**
     * vcPhoneticUk
     */
    @TableField(value = "vc_phonetic_uk")
    private String vcPhoneticUk;

    /**
     * vcPhoneticUs
     */
    @TableField(value = "vc_phonetic_us")
    private String vcPhoneticUs;

    /**
     * vcPronunciation
     */
    @TableField(value = "vc_pronunciation")
    private String vcPronunciation;

    /**
     * vcInterpretation
     */
    @TableField(value = "vc_interpretation")
    private String vcInterpretation;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_VC_ID = "vc_id";

    public static final String COL_ORIGINAL_ID = "original_id";

    public static final String COL_VC_VOCABULARY = "vc_vocabulary";

    public static final String COL_VC_INITIAL = "vc_initial";

    public static final String COL_VC_FREQUENCY = "vc_frequency";

    public static final String COL_VC_DIFFICULTY = "vc_difficulty";

    public static final String COL_VC_PHRASE = "vc_phrase";

    public static final String COL_VC_UPDATED_TIME = "vc_updated_time";

    public static final String COL_VC_STUDY_USER_COUNT = "vc_study_user_count";

    public static final String COL_VC_ACKNOWLEDGE_RATE = "vc_acknowledge_rate";

    public static final String COL_STATUS = "status";

    public static final String COL_VC_PHONETIC_UK = "vc_phonetic_uk";

    public static final String COL_VC_PHONETIC_US = "vc_phonetic_us";

    public static final String COL_VC_PRONUNCIATION = "vc_pronunciation";

    public static final String COL_VC_INTERPRETATION = "vc_interpretation";
}