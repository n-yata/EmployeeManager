package util;

/**
 * 都道府県列挙クラス
 */
public enum Prefecture {
	HOKKAIDO (1, "北海道"),
	AOMORI (2, "青森"),
	IWATE (3, "岩手"),
	MIYAGI(4, "宮城"),
	AKITA(5, "秋田"),
	YAMAGATA(6, "山形"),
	FUKUSHIMA(7, "福島"),
	IBARAKI (8, "茨城"),
	TOCHIGI (9, "栃木"),
	GUNMA(10, "群馬"),
	SAITAMA (11, "埼玉"),
	CHIBA(12, "千葉"),
	TOKYO(13, "東京"),
	KANAGAWA(14, "神奈川"),
	NIIGATA (15, "新潟"),
	TOYAMA(16, "富山"),
	ISHIKAWA(17, "石川"),
	FUKUI(18, "福井"),
	YAMANASHI(19, "山梨"),
	NAGANO(20, "長野"),
	GIFU(21, "岐阜"),
	SHIZUOKA(22, "静岡"),
	AICHI(23, "愛知"),
	MIE (24, "三重"),
	SHIGA(25, "滋賀"),
	KYOTO(26, "京都"),
	OSAKA(27, "大阪"),
	HYOGO(28, "兵庫"),
	NARA(29, "奈良"),
	WAKAYAMA(30, "和歌山"),
	TOTTORI (31, "鳥取"),
	SHIMANE (32, "島根"),
	OKAYAMA (33, "岡山"),
	HIROSHIMA(34, "広島"),
	YAMAGUCHI(35, "山口"),
	TOKUSHIMA(36, "徳島"),
	KAGAWA(37, "香川"),
	EHIME(38, "愛媛"),
	KOCHI(39, "高知"),
	FUKUOKA (40, "福岡"),
	SAGA(41, "佐賀"),
	NAGASAKI(42, "長崎"),
	KUMAMOTO(43, "熊本"),
	OITA(44, "大分"),
	MIYAZAKI(45, "宮崎"),
	KAGOSHIMA(46, "鹿児島"),
	OKINAWA (47, "沖縄"),
;

private final int code_;
private final String text_;

private Prefecture(int code, String text) {
code_ = code;
text_ = text;
}

/**
 * 都道府県コードを取得します。
 *
 * @return 都道府県コード
 */
public int getCode() {
return code_;
}

/**
 * 都道府県名を取得します。
 * "都"、"府"、"県"は含みません。
 *
 * @return 都道府県名
 */
public String getText() {
return text_;
}

/**
 * 都道府県名を取得します。
 * "都"、"府"、"県"を含みます。
 *
 * @return 都道府県名
 */
public String getFullText() {
switch (this) {
case HOKKAIDO:
return getText();
case TOKYO:
return getText() + "都";
case KYOTO:
case OSAKA:
return getText() + "府";
default:
return getText() + "県";
}
}

/**
 * 指定されたコードのPrefectureオブジェクトを取得します。
 * 存在しない場合はnullを返します。
 *
 * @param code都道府県コード
 * @return Prefectureオブジェクト
 */
public static Prefecture getByCode(int code) {
for (Prefecture p : Prefecture.values()) {
if (p.getCode() == code) return p;
}
return null;
}

/**
 * 指定された都道府県名（"都"、"府"、"県"は含まない）のPrefectureオブジェクトを取得します。
 * 存在しない場合はnullを返します。
 *
 * @param text都道府県名
 * @return Prefectureオブジェクト
 */
public static Prefecture getByText(String text) {
for (Prefecture p : Prefecture.values()) {
if (p.getText().equals(text)) return p;
}
return null;
}

/**
 * 指定された都道府県名（"都"、"府"、"県"は含む）のPrefectureオブジェクトを取得します。
 * 存在しない場合はnullを返します。
 *
 * @param fullText都道府県名
 * @return Prefectureオブジェクト
 */
public static Prefecture getByFullText(String fullText) {
for (Prefecture p : Prefecture.values()) {
if (p.getFullText().equals(fullText)) return p;
}
return null;
}
}
