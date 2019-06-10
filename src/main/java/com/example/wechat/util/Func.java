package com.example.wechat.util;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 公共函数类库
 * @author xieli
 *
 */
public class Func {
	/**
	 * 浮点型零值（判断"0.0"值）
	 */
	public static final double FLOAT_ZERO_VALUE = 0.0000001;

	public static final String PATTERN = "^[-]?([0-9]+)(\\.[0-9]+)?$";

	public static final String PATTERN_DOT_2 = "^[-]?([0-9]+)(\\.[0-9]{1,2}+)?$";

	public static final String PATTERN_DOT_3 = "^[-]?([0-9]+)(\\.[0-9]{1,3}+)?$";

	public static final String PATTERN_NUMDOT = "^\\d+(\\.\\d+)?+(\\.\\d+)?+(\\.\\d+)?+(\\.\\d+)?+(\\.\\d+)?";

	private static DecimalFormat df = new DecimalFormat("#.##########");

	/**
	 * 加法运算（准确计算精度）
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static double add(Object o1, Object o2)
	{
		BigDecimal b1 = null;
		BigDecimal b2 = null;
		try
		{
			b1 = new BigDecimal(Func.parseStr(o1));
		} catch (Exception e)
		{
			b1 = new BigDecimal(0);
		}
		try
		{
			b2 = new BigDecimal(Func.parseStr(o2));
		} catch (Exception e)
		{
			b2 = new BigDecimal(0);
		}
		BigDecimal result = null;
		try
		{
			result = b1.add(b2);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		double douValue = 0d;
		if (result != null)
		{
			douValue = result.doubleValue();
		}
		return douValue;
	}

	public static BigDecimal add(BigDecimal b1, BigDecimal b2)
	{
		if (b1 == null)
		{
			b1 = new BigDecimal(0);
		}
		if (b2 == null)
		{
			b2 = new BigDecimal(0);
		}

		BigDecimal result = null;
		try
		{
			result = b1.add(b2);
		} catch (Exception e)
		{
			e.printStackTrace();
			result = new BigDecimal(0);
		}
		return result;
	}

	/**
	 * 检查用户数据数据长度是否在指定的范围内
	 * @param data
	 * @param length
	 * @return
	 */
	public static boolean checkDataLength(Object data, int length)
	{
		String dataStr = Func.parseStr(data);
		if (dataStr.trim().length() > length)
		{
			return false;
		}

		return true;
	}

	/**
	 * 判断两个double类型的数据是否相等
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public static boolean checkDblEqule(double arg1, double arg2)
	{
		return Math.abs(arg1 - arg2) > Func.FLOAT_ZERO_VALUE;
	}

	/**
	 * 检查浮点型数据是否为"0.0"值
	 */
	public static boolean checkDblZero(double dblIn)
	{
		return dblIn < Func.FLOAT_ZERO_VALUE && dblIn > -Func.FLOAT_ZERO_VALUE;
	}

	/*
	 * 判断是否为浮点数，包括double和float
	 * @param str 传入的字符串
	 * @return 是浮点数返回true,否则返回false
	 */
	public static boolean checkDouble(String str)
	{
		// Pattern pattern = Pattern.compile("[0-9]*((\\.{1})[0-9]+)?");
		Pattern pattern = Pattern.compile("^([\\+]?[\\d]*|[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$");
		return pattern.matcher(str).matches();
	}

	/*
	 * 判断是否为负整数
	 * @param str 传入的字符串
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean checkFuInteger(String str)
	{
		Pattern pattern = Pattern.compile("^[\\-]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/*
	 * 判断是否为整数
	 * @param str 传入的字符串
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean checkInteger(String str)
	{
		Pattern pattern = Pattern.compile("^[\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 检测字符串是否为null或空字符串或“null”字符串
	 * @param str 需要检测的字符串
	 * @return 如果字符串为null或空字串或trim()后为空字串：true；否则返回false
	 */
	public static boolean checkNull(String str)
	{
		if (str == null || "null".equals(str) || "undefined".equals(str))
		{
			return true;
		}

		return str.trim().length() <= 0;
	}

	public static boolean checkNullOrEmpty(Object str)
	{
		if (str == null)
		{
			return true;
		}

		String value = Func.parseStr(str);
		if (value.trim().toLowerCase().equals("null") || value.trim().toLowerCase().equals("undefined"))
		{
			return true;
		} else
		{
			return value.trim().length() <= 0;
		}
	}

	public static List<Map<String, Object>> copyMap(List<Map<String, Object>> target)
	{
		if (target == null)
		{
			return null;
		}
		List<Map<String, Object>> source = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : target)
		{
			Map<String, Object> newMap = new HashMap<String, Object>();
			Func.copyMap(newMap, map);
			source.add(newMap);
		}
		return source;
	}

	public static void copyMap(Map<String, Object> target, Map<String, Object> source)
	{
		if (target == null || source == null || source.size() < 1)
		{
			return;
		}
		for (Entry<String, Object> entry : source.entrySet())
		{
			target.put(entry.getKey(), entry.getValue());
		}
	}

	public static boolean deleteDirectory(String sPath)
	{
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator))
		{
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory())
		{
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (File file : files)
		{
			// 删除子文件
			if (file.isFile())
			{
				flag = Func.deleteFile(file.getAbsolutePath());
				if (!flag)
				{
					break;
				}
			} // 删除子目录
			else
			{
				flag = Func.deleteDirectory(file.getAbsolutePath());
				if (!flag)
				{
					break;
				}
			}
		}
		if (!flag)
		{
			return false;
		}
		return dirFile.delete();
	}

	public static boolean deleteDirFile(String sPath)
	{
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator))
		{
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory())
		{
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (File file : files)
		{
			// 删除子文件
			if (file.isFile())
			{
				flag = Func.deleteFile(file.getAbsolutePath());
				if (!flag)
				{
					break;
				}
			} // 删除子目录
			else
			{
				flag = Func.deleteDirectory(file.getAbsolutePath());
				if (!flag)
				{
					break;
				}
			}
		}
		if (!flag)
		{
			return false;
		}

		return true;
	}

	public static boolean deleteFile(String sPath)
	{
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists())
		{
			flag = file.delete();
		}
		return flag;
	}

	/**
	 * 将指定字符串，通过指定格式转换为日期对象.
	 * @param dateStr .
	 */
	public static Date parseDate(String dateStr)
	{
		if (Func.checkNullOrEmpty(dateStr))
		{
			return null;
		}
		try
		{
			return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}

		try
		{
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
		} catch (ParseException e)
		{
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", java.util.Locale.US);
				return sdf.parse(dateStr);
			} catch (ParseException e1)
			{
			}
		}
		return null;
	}

	/**
	 * 除法运算（准确计算精度）
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static double divide(Object o1, Object o2, int... len)
	{
		int i = 10; // 默认取两位
		if (len.length > 0)
		{
			i = len[0];
		}
		BigDecimal b1 = null;
		BigDecimal b2 = null;
		if (Func.checkNullOrEmpty(o1))
		{
			b1 = new BigDecimal(0);
		} else
		{
			b1 = new BigDecimal(Func.parseStr(o1));
		}
		if (Func.checkNullOrEmpty(o2))
		{
			return 0;
			// b2 = new BigDecimal(0);
		} else
		{
			b2 = new BigDecimal(Func.parseStr(o2));
		}
		if (b2.doubleValue() == 0.0)
		{
			return 0;
		}
		BigDecimal result = b1.divide(b2, i, BigDecimal.ROUND_HALF_UP);

		return result.doubleValue();
	}

	public static String formatData(Date date, String format)
	{
		if (date == null)
		{
			date = new Date();
		}
		if (Func.checkNullOrEmpty(format))
		{
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 获取常量类常量列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> getConstFieldList(Class clasz) throws Exception
	{
		if (clasz == null)
		{
			return null;
		}
		Field[] field = clasz.getDeclaredFields();
		List<String> constStrList = new ArrayList<String>();
		// 获取常量字段
		for (Field element : field)
		{
			constStrList.add(element.get(null).toString());
		}

		return constStrList;
	}

	/**
	 * 通过枚举值获取对应的枚举
	 * @param <E> 枚举泛型
	 * @param enumType 枚举类型
	 * @param convertValue 需要转换的值
	 * @param defaultValue 转换失败的默认值
	 * @return
	 */
	public static <E extends Enum<E>> E getEnumByName(Class<E> enumType, Object convertValue, E defaultValue)
	{
		if (convertValue == null)
		{
			return defaultValue;
		}

		String convertStr = Func.parseStr(convertValue);
		// 遍历枚举类型名称集合
		for (E item : EnumSet.allOf(enumType))
		{
			if (item.name().equals(convertStr))
			{
				return item;
			}
		}

		return defaultValue;
	}

	/**
	 * 获取文件对应无后缀的文件名称
	 * @param fileName
	 * @return
	 */
	public static String getFileExtName(String fileName)
	{
		if (Func.checkNullOrEmpty(fileName))
		{
			return "";
		}

		String fileExtName = "";
		if (fileName.lastIndexOf(".") >= 0)
		{
			fileExtName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		}

		return fileExtName;
	}

	/**
	 * 获取文件对应无后缀的文件名称
	 * @param file
	 * @return
	 */
	public static String getFileName(File file)
	{
		if (file == null)
		{
			return "";
		}

		String fileName = "";
		if (file.getName().lastIndexOf(".") >= 0)
		{
			fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
		} else
		{
			fileName = file.getName();
		}

		return fileName;
	}

	@SuppressWarnings("rawtypes")
	public static String getListSplitSqlStr(List list, String split)
	{
		if (list == null || Func.checkNullOrEmpty(split))
		{
			return "";
		}
		StringBuffer strBuff = new StringBuffer();
		for (Iterator iterator = list.iterator(); iterator.hasNext();)
		{
			String str = (String) iterator.next();
			if (strBuff.length() > 0)
			{
				strBuff.append(new StringBuilder(String.valueOf(split)).append("'").append(str).append("'").toString());
			} else
			{
				strBuff.append(new StringBuilder("'").append(str).append("'").toString());
			}
		}

		return strBuff.toString();
	}

	@SuppressWarnings("rawtypes")
	public static String getListSplitStr(List list, String split)
	{
		if (list == null || Func.checkNullOrEmpty(split))
		{
			return "";
		}
		StringBuffer strBuff = new StringBuffer();
		for (Iterator iterator = list.iterator(); iterator.hasNext();)
		{
			String str = (String) iterator.next();
			if (strBuff.length() > 0)
			{
				strBuff.append(new StringBuilder(String.valueOf(split)).append(str).toString());
			} else
			{
				strBuff.append(str);
			}
		}

		return strBuff.toString();
	}

	public static String getProjectFilePath(String str) throws Exception
	{
		URL url = Func.class.getClassLoader().getResource("");
		String path = url.getPath();
		path = path.substring(0, path.indexOf("WEB-INF")) + str + "/";
		File file = new File(path);
		if (!file.exists())
		{
			file.mkdir();
		}
		return path;
	}

	/**
	 * 正则表达式，匹配正负数
	 * @param str
	 * @return
	 */
	public static boolean matches(String str)
	{
		return str.matches(Func.PATTERN);
	}

	/**
	 * 正则表达式，匹配正负数，小数点 ps:小数位长度小于等于2位
	 * @param str
	 * @return
	 */
	public static boolean matchesDot2(String str)
	{
		return str.matches(Func.PATTERN_DOT_2);
	}

	/**
	 * 正则表达式，匹配正负数，小数点 ps:小数位长度小于等于3位
	 * @param str
	 * @return
	 */
	public static boolean matchesDot3(String str)
	{
		return str.matches(Func.PATTERN_DOT_3);
	}

	/**
	 * 匹配数字和小数点，最多达到1.1.1.1.1.1级别
	 * @param value
	 * @return
	 */
	public static boolean matchesNumDot(String value)
	{
		if (Func.checkNullOrEmpty(value))
		{
			return false;
		}
		return value.matches(Func.PATTERN_NUMDOT);
	}

	/**
	 * 乘法运算（准确计算精度）
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static double multiply(Object o1, Object o2)
	{
		if (Func.checkNullOrEmpty(Func.parseStr(o1)) || Func.checkNullOrEmpty(Func.parseStr(o2)))
		{
			return 0;
		}
		BigDecimal b1 = new BigDecimal(Func.parseStr(o1));
		BigDecimal b2 = new BigDecimal(Func.parseStr(o2));
		BigDecimal result = b1.multiply(b2);
		if (result != null)
		{
			return result.doubleValue();
		}

		return 0;
	}

	/**
	 * 已废弃，建议使用this.getClassUUID();
	 */
	public static String newGuid()
	{
		return UUID.randomUUID().toString();
	}

	public static boolean parseBoolean(Object objIn)
	{
		return Func.parseBoolean(objIn, false);
	}

	public static boolean parseBoolean(Object objIn, boolean defaultValue)
	{
		boolean bRe = defaultValue;
		if (objIn == null)
		{
			return bRe;
		}
		try
		{
			bRe = Boolean.parseBoolean(Func.parseStr(objIn));
		} catch (Exception exception)
		{
		}
		return bRe;
	}

	public static double parseDbl(Object objIn)
	{
		double dbl = 0.0D;
		if (objIn == null)
		{
			return dbl;
		}
		try
		{
			dbl = Double.parseDouble(Func.parseStr(objIn));
		} catch (Exception exception)
		{
		}
		return dbl;
	}

	/**
	 * 解析浮点型字符串(对应值为零则返回空)
	 */
	public static String parseDblStr(Object objIn, int carryNum)
	{
		// 当为0的时候显示为空
		if (Func.parseDbl(objIn) == 0.0D)
		{
			return "";
		}
		return Func.setScale(objIn, carryNum);

		// Double dbl = Func.parseDbl(objIn);
		// if (CommFunc.CheckDblZero(dbl)) return "";
		//
		// // 进行精度处理
		// DecimalFormat decFormat = new DecimalFormat();
		// decFormat.setMinimumFractionDigits(carryNum);
		// decFormat.setMaximumFractionDigits(carryNum);
		// return decFormat.format(dbl);
	}

	/**
	 * 解析浮点型字符串(对应值为零则返回空)
	 */
	public static String parseDblStr(Object objIn, int carryNum, boolean bDisplayZero)
	{
		double dbl = Func.round(objIn, carryNum);

		if (!bDisplayZero && Func.checkDblZero(dbl))
		{
			return "";
		}

		// 进行精度处理
		DecimalFormat decFormat = new DecimalFormat();
		decFormat.setMinimumFractionDigits(carryNum);
		decFormat.setMaximumFractionDigits(carryNum);
		if (carryNum == 2)
		{
			decFormat.applyPattern("0.00");
		} else
		{
			decFormat.applyPattern("#.#");
		}
		return decFormat.format(dbl);
	}

	public static float parseFloat(Object objIn)
	{
		float flt = 0.0F;
		if (objIn == null)
		{
			return flt;
		}
		try
		{
			flt = Float.parseFloat(Func.parseStr(objIn));
		} catch (Exception exception)
		{
		}
		return flt;
	}

	public static int parseInt(Object objIn)
	{
		return (int) Func.parseDbl(objIn);
	}

	public static long parseLong(Object objIn)
	{
		return (long) Func.parseDbl(objIn);
	}

	public static String parseStr(Object objIn)
	{
		if (objIn == null)
		{
			return "";
		} else
		{
			return objIn.toString().trim();
		}
	}

	/**
	 * 判断code编码是否在startCode和endCode之间
	 * @param code
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	public static boolean rangeParse(String code, String startCode, String endCode)
	{
		int a = startCode.compareToIgnoreCase(code);
		int b = endCode.compareToIgnoreCase(code);
		if (a < 0 && b < 0 || a > 0 && b > 0)
		{
			return false;
		}
		return true;
	}

	/**
	 * 字符串转译
	 * @param source
	 * @return
	 */
	public static String replaceAllTsStr(String source)
	{
		// 替换掉空格，中文的字符转换成对应的英文字符
		source = source.replaceAll(" ", "").replaceAll("（", "(").replaceAll("）", ")").replaceAll("\n", "");
		return source;
	}

	public static String replaceAllTsStr2(String source)
	{
		source = source.replaceAll("\\[", "").replaceAll("【", "").replaceAll("\\]", "").replaceAll("】", "");
		return source;
	}

	/**
	 * 四舍五入运算
	 * @param o1
	 * @param round
	 * @return
	 */
	public static double round(Object o1, int... round)
	{
		int len = 2; // 默认取两位
		double v = 1;
		if (round.length > 0)
		{
			len = round[0];
		}
		for (int i = 0; i < len; i++)
		{
			v *= 10;
		}
		BigDecimal b = new BigDecimal(Func.parseStr(o1));
		BigDecimal val = new BigDecimal(v);
		BigDecimal result = b.multiply(val).divide(val, len, BigDecimal.ROUND_HALF_UP);

		return result.doubleValue();
	}

	/**
	 * 设置数值小数位数
	 * @param dbl
	 * @param carryNum
	 * @return
	 */
	public static double setDoubleScale(double dbl, int carryNum)
	{
		if (carryNum < 0)
		{
			return dbl;
		}

		return BigDecimal.valueOf(dbl).setScale(carryNum, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 设置小数位数
	 * @param data 需要转换的数据
	 * @param round 有效小数位数（默认为两位）
	 * @return
	 */
	public static String setScale(Object data, int... round)
	{
		// // 当为0的时候显示为空
		// if (Func.parseDbl(data) == 0.0D)
		// {
		// return "";
		// }
		double re = 0d;
		try
		{
			re = Func.parseDbl(data);
		} catch (Exception e)
		{
			return Func.parseStr(data);
		}
		if (re == 0d)
		{
			return "0";
		}

		if (round.length <= 0)
		{
			round = new int[]
					{ 2 }; // 默认取两位小数
		}
		try
		{
			return Func.df.format(BigDecimal.valueOf(re).setScale(round[0], BigDecimal.ROUND_HALF_UP));
		} catch (Exception e)
		{
		}
		return "";
	}

	/**
	 * 过滤掉特殊字符
	 * @param str
	 * @return
	 */
	public static String stringFilter(String str)
	{
		if (Func.checkNullOrEmpty(str))
		{
			return str;
		}
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 减法运算（准确计算精度）
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static double subtract(Object o1, Object o2)
	{
		BigDecimal b1 = null;
		BigDecimal b2 = null;
		try
		{
			b1 = new BigDecimal(Func.parseStr(o1));
		} catch (Exception e)
		{
			b1 = new BigDecimal(0);
		}
		try
		{
			b2 = new BigDecimal(Func.parseStr(o2));
		} catch (Exception e)
		{
			b2 = new BigDecimal(0);
		}
		BigDecimal result = null;
		try
		{
			result = b1.subtract(b2);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		double douValue = 0d;
		if (result != null)
		{
			douValue = result.doubleValue();
		}
		return douValue;
	}

	/**
	 * 将传入的Params的filter字符串转换成json字符串
	 * @param filter 例： usrid=1,name=myname,code=123
	 * @return
	 */
	@SuppressWarnings(
			{ "rawtypes", "unchecked" })
	public static Map<String, String> transferJsonToMap(String filter, String splitStr)
	{
		Map map = new HashMap<String, String>();
		// 根据“&”切割,获取各属性名称及值
		String[] items = filter.split(splitStr);
		if (items.length <= 0)
		{
			return map;
		}

		for (String tmpStr : items)
		{
			// 根据“=”切割,划分名称与值
			String[] tmpItem = tmpStr.split("=");
			if (tmpItem.length <= 1)
			{
				continue;
			}

			// 加入map
			map.put(tmpItem[0], tmpItem[1]);
		}

		return map;
	}

	/**
	 * 将传入的Params的filter字符串转换成json字符串
	 * @param filter 例： usrid=1&name=myname&code=123
	 * @return {userid:1,name:myname,code:123}
	 */
	public static String transferToJson(String filter)
	{
		// 根据“&”切割,获取各属性名称及值
		String[] items = filter.split("&");
		if (items.length <= 0)
		{
			return "{}";
		}
		StringBuffer buffer = new StringBuffer();

		buffer.append("{");
		for (String tmpStr : items)
		{
			// 根据“=”切割,划分名称与值
			String[] tmpItem = tmpStr.split("=");
			if (tmpItem.length <= 1)
			{
				continue;
			}

			// 构造json
			buffer.append("\"" + tmpItem[0] + "\":" + "\"" + tmpItem[1] + "\",");
		}
		String json = buffer.toString();
		// 去除最后的","，并加上"}"
		if (json.lastIndexOf(",") == json.length() - 1)
		{
			json = json.substring(0, json.length() - 1) + "}";
		} else
		{
			json += "}";
		}

		return json;
	}

	/**
	 * 尝试转换Double值数据
	 * @param parseObject
	 * @return
	 */
	public static double tryParseDouble(String parseObject)
	{
		double parseValue = Double.MIN_VALUE;
		if (parseObject == null)
		{
			return parseValue;
		}

		try
		{
			// 尝试转换值
			parseValue = Double.valueOf(parseObject);
			return parseValue;
		} catch (Exception e)
		{
			return parseValue;
		}
	}

	public static String getFirstImage(String image)
	{
		try
		{
			if (Func.checkNullOrEmpty(image))
			{
				return image;
			}

			String[] is = image.split(",");
			if (is == null || is.length < 1)
			{
				return image;
			}

			return is[0];
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return image;
	}



	public static String convertCodeUtf8(String value)
	{
		try
		{
			if (checkNullOrEmpty(value))
			{
				return "";
			}

			return new String(parseStr(value).getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e)
		{
			return "";
		}
	}

	/**
	 * 将set转化成数组
	 * @param set set集合
	 * @return 返回数组
	 */
	public static String[] setToArray(Set<String> set)
	{
		if (set == null || set.isEmpty())
			return new String[0];

		String[] strArray = new String[set.size()];
		Object[] obj = set.toArray();
		for (int i = 0; i < obj.length; i++)
		{
			strArray[i] = parseStr(obj[i]);
		}
		return strArray;
	}

	/**
	 * 将set转化成数组
	 * @param list set集合
	 * @return 返回数组
	 */
	public static String[] listToArray(List<String> list)
	{
		if (list == null || list.isEmpty())
			return null;

		String[] strArray = new String[list.size()];
		Object[] obj = list.toArray();
		for (int i = 0; i < obj.length; i++)
		{
			strArray[i] = parseStr(obj[i]);
		}
		return strArray;
	}

	/**
	 * 通过","，进行转换， 将数组对象转化成list集合对象,
	 * @param param
	 * @return
	 */
	public static List<String> strToList(String param)
	{
		String paramData = param.replace("，", ",");
		return strToListBySeparator(paramData, ",");
	}

	/**
	 * 通过separator，进行转换， 将数组对象转化成list集合对象
	 * @param param
	 * @return
	 */
	public static List<String> strToListBySeparator(String param, String separator)
	{
		if (checkNullOrEmpty(param) || checkNullOrEmpty(separator))
		{
			return new ArrayList<String>();
		}

		return arrayToList(param.split(separator));
	}

	/**
	 * 将数组转化成List
	 * @param array 数组
	 * @return
	 */
	public static List<String> arrayToList(String[] array)
	{
		if (array == null || array.length <= 0)
		{
			return new ArrayList<String>();
		}

		List<String> result = new ArrayList<String>();
		for (String str : array)
		{
			if (checkNullOrEmpty(str))
			{
				continue;
			}

			result.add(parseStr(str));
		}
		return result;
	}


	/**
	 * 判断当前数据是否是数字
	 * @param obj 传入数据
	 * @return
	 */
	public static boolean isNumeric(Object obj)
	{
		String str = parseStr(obj);
		try
		{
			Double.parseDouble(str);
			return true;
		} catch (Exception e)
		{

		}

		return false;

	}



	/**
	 * 将ids转化成set集合“，”
	 * @param ids
	 * @return
	 */
	public static Set<String> idsSet(String ids)
	{
		if (Func.checkNull(ids))
			return new HashSet<String>();

		Set<String> set = new HashSet<String>();
		String[] idsArr = ids.split(",");
		for (int i = 0; i < idsArr.length; i++)
		{
			if (Func.checkNullOrEmpty(idsArr[i]))
				continue;

			set.add(Func.parseStr(idsArr[i]));
		}
		return set;
	}

	/**
	 * 将别名字符串转化成set集合“，”
	 * @param aliaseName
	 * @return
	 */
	public static Set<String> strToSet(String aliaseName)
	{
		if (Func.checkNull(aliaseName))
			return new HashSet<String>();

		Set<String> set = new HashSet<String>();
		String[] aliasArr = aliaseName.split(";");
		for (int i = 0; i < aliasArr.length; i++)
		{
			if (Func.checkNullOrEmpty(aliasArr[i]))
				continue;

			set.add(Func.parseStr(aliasArr[i]));
		}
		return set;
	}

	/**
	 * 动态识别单位
	 * @param dwMap
	 * @param dw
	 * @return
	 */
	public static String dynamicRecognition(Map<String, String> dwMap, String dw)
	{
		if (dwMap == null || dwMap.isEmpty() || Func.checkNullOrEmpty(dw))
			return null;

		if (dwMap.containsKey(dw))
			return dw;

		String aliaseName = null;
		Set<String> aliaseSetName = new HashSet<String>();
		for (Entry<String, String> entry : dwMap.entrySet())
		{
			aliaseName = entry.getValue();
			if (Func.checkNullOrEmpty(aliaseName))
				continue;

			aliaseSetName = Func.strToSet(aliaseName);
			if (aliaseSetName.contains(dw))
				return Func.parseStr(entry.getKey());
		}

		return "";
	}

	/**
	 * 获取一个8位的UUID值
	 * @return
	 */
	public static String get8UUID()
	{
		UUID id = UUID.randomUUID();
		String[] idd = id.toString().split("-");
		return idd[0];
	}

	public static String replaceSpecChar(Object v)
	{
		String str = Func.parseStr(v);
		if (!Func.checkNullOrEmpty(str))
		{
			return str.replaceAll("：", ":").replaceAll("（", "(").replaceAll("）", ")").replaceAll("；", ";")
					.replaceAll("。", ".").replaceAll("？", "?").replaceAll("！", "!").replaceAll("×", "*")
					.replaceAll("“", "\"").replaceAll("”", "\"").replaceAll("吨", "t").replaceAll("Km", "km")
					.replaceAll("，", ",").replaceAll("千伏", "kV").replaceAll("千米", "km").replaceAll("米", "m")
					.replaceAll("M", "m").replaceAll("KM", "km").replaceAll("kM", "km").replaceAll("m²", "m2")
					.replaceAll("mm²", "mm2").replaceAll("mm³", "mm3").replaceAll("m³", "m3");
		}
		return str;
	}

	/**
	 * 进行数字兼容
	 * @param dydj
	 * @return
	 */
	public static String compatibleVoltage(String dydj)
	{
		if (checkNullOrEmpty(dydj))
			return "";

		if (dydj.contains("35"))
		{
			return "35kV";
		} else if (dydj.contains("110"))
		{
			return "110kV";
		} else if (dydj.contains("220"))
		{
			return "220kV";
		} else if (dydj.contains("500"))
		{
			return "500kV";
		} else if (dydj.contains("10"))
		{
			return "10kV";
		}

		return "";
	}

	public static String replaceQuantities(Object v)
	{
		String str = Func.parseStr(v);
		if (!Func.checkNullOrEmpty(str))
		{
			return str.replaceAll("：", ":").replaceAll("（", "(").replaceAll("）", ")").replaceAll("；", ";")
					.replaceAll("。", ".").replaceAll("？", "?").replaceAll("！", "!").replaceAll("“", "\"")
					.replaceAll("”", "\"").replaceAll("吨", "t").replaceAll("Km", "km").replaceAll("KM", "km")
					.replaceAll("kM", "km").replaceAll("，", ",").replaceAll("\n", "").replaceAll("\r", "")
					.replaceAll("千伏", "kV").replaceAll("Kv", "kV").replaceAll("KV", "kV").replaceAll("千米", "km")
					.replaceAll("米", "m").replaceAll("M", "m").replaceAll("m²", "m2").replaceAll("mm²", "mm2")
					.replaceAll("mm³", "mm3").replaceAll("m³", "m3");
		}
		return str;
	}

	/**
	 * 通过","，进行转换， 将数组对象转化成list集合对象,并将每一个值添加进一个list中
	 * @param result
	 * @return
	 */
	public static void strIntoList(String param, List<String> result)
	{
		if (checkNullOrEmpty(param))
			return;

		String[] array = param.split(",");
		if (array == null || array.length <= 0)
			return;

		for (String str : array)
		{
			if (checkNullOrEmpty(str))
				continue;
			result.add(str);
		}
	}

	/**
	 * 通过","，进行转换， 将数组对象转化成list集合对象,并将每一个值添加进一个list中
	 * @param result
	 * @return
	 */
	public static void strIntoSet(String param, Set<String> result)
	{
		if (checkNullOrEmpty(param))
			return;

		String[] array = param.split(",");
		if (array == null || array.length <= 0)
			return;

		for (String str : array)
		{
			if (checkNullOrEmpty(str))
				continue;
			result.add(str);
		}
	}

	/**
	 * 符号转换, 中英文之间符号转换
	 * @param str
	 * @return
	 */
	public static String singleConvert(String str)
	{
		if (checkNullOrEmpty(str))
		{
			return null;
		}

		if (str.equals(","))
		{
			return "，";
		} else if (str.equals("，"))
		{
			return ",";
		} else if (str.equals("."))
		{
			return "。";
		} else if (str.equals("。"))
		{
			return ".";
		} else if (str.equals("、"))
		{
			return "、";
		}

		return null;
	}

	/**
	 * 替换字符串中的制表、换行等符号。
	 */
	public static String replaceBlank(String str)
	{

		String dest = "";

		if (str != null)
		{
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");

			Matcher m = p.matcher(str);

			dest = m.replaceAll("");
		}

		return dest;

	}


	/**
	 * 替换字符串中的制表、换行等符号。
	 */
	public static String replaceSpecBlank(String str)
	{

		String dest = "";

		if (str != null)
		{
			Pattern p = Pattern.compile("\\t|\n");

			Matcher m = p.matcher(str);

			dest = m.replaceAll("");
		}

		return dest;

	}
}
