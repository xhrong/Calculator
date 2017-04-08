package home.jmstudios.calc;

import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.List;
import java.util.Map;
import com.udojava.evalex.Expression;
import com.udojava.evalex.Expression.Function;
import home.jmstudios.calc.R;
import home.jmstudios.calc.R.id;
import home.jmstudios.calc.R.layout;
import home.jmstudios.calc.R.menu;
import home.jmstudios.calc.R.style;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {
	EditText editText, editTextQ;
	final Context context = this;

	SlidingDrawer slidingdrawer;

	LinearLayout linearlayout1;

	boolean wrong = false, firsttime = true, visbutton = false, vs = false,
			js = false, r = false, b = false, no_calc = true;

	Button button0, button1, button2, button3, button4, button5, button6,
			button7, button8, button9, buttonPlus, buttonMinus, buttonMultiply,
			buttonDivide, buttonPoint, buttonDel, buttonReset, button_sin,
			button_cos, button_tan, button_squared_2, button_root, button_del,
			button_dec, button_bin, button_pi, buttonEqual;

	String sum = "", one, oneAgain = "", two, twoAgain = "", three,
			threeAgain = "", four, fourAgain = "", five, fiveAgain = "", six,
			sixAgain, seven, sevenAgain = "", eight, eightAgain = "", nine,
			nineAgain = "", zero, plus1, minus, multiply, divide, equal, point,
			del, reset, dec_string = "", hex_string = "", oct_string = "",
			pi = "3.14159265359", calc = "";

	Integer countOne = 0, dec_num, unicode_value;

	Float result = 0f, result_mul = 1f, result_div = 1f;

	int pressCount = 1, sumZero, dec_flag = 0, c, i, pl = 0, cursor;

	char press;

	String EditTextMsg, bin_num, hex_num, oct_num;

	private String theme_settings;
	private boolean colored_navigation_bar, colored_notification_bar;
	
	private String precision_str = "10";
	private int precision;
	private static int precision_default = 10;
	
	private String angle_settings;
	private String angle_type = "degrees";

	Float floatEditTextMsg;

	Double doubleEditTextMsg, afterSin, after_cos, after_tan,
			toRadian_doubleEditTextMsg, after_squared_2, after_root,
			after_qube;

	Vibrator vibrator;

	int sdk = android.os.Build.VERSION.SDK_INT;
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_about:
			Intent aboutintent = (Intent) new Intent(this, About.class);
			startActivity(aboutintent);
			return true;

		case R.id.menu_settings:
			Intent preferencesintent = (Intent) new Intent(this,
					Preferences.class);
			startActivity(preferencesintent);
			return true;
			



		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(Main.this);
		String new_theme_settings = prefs.getString("theme", "a");
		boolean new_colored_notification_bar = prefs.getBoolean("colored_notification_bar", true);
		boolean new_colored_navigation_bar = prefs.getBoolean("colored_navigation_bar", false);
		if (new_theme_settings != theme_settings
				|| new_colored_notification_bar != colored_notification_bar
				|| new_colored_navigation_bar != colored_navigation_bar) {
			Intent intent = getIntent();
			finish();
			startActivity(intent);
			System.out.println("change");
		}
		String new_precision_settings = prefs.getString("precision", precision_str);
		if (new_precision_settings != precision_str) {
			Intent intent = getIntent();
			finish();
			startActivity(intent);
			System.out.println("change");
		}
		String new_angle_settings = prefs.getString("angle", "a");
		if (new_angle_settings != angle_settings) {
			Intent intent = getIntent();
			finish();
			startActivity(intent);
			System.out.println("change");
		}


	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(Main.this);
		theme_settings = prefs.getString("theme", "a");
		colored_notification_bar = prefs.getBoolean("colored_notification_bar", true);
		colored_navigation_bar = prefs.getBoolean("colored_navigation_bar", false);
		String theme = "standard";
		
		precision_str = prefs.getString("precision", precision_str);
		
		angle_settings = prefs.getString("angle", "a");
		
		try {
			precision = Integer.parseInt(precision_str);
		} catch (Exception err) {
			precision = precision_default;
			Context context = getApplicationContext();
			CharSequence text = "The specified precision is not a integer, change this in the settings.";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		
		if (((prefs.getString("theme", "a")).charAt(0) == 'b')) {
			setTheme(R.style.AppThemeBlue);
			setContentView(R.layout.mainblue);
			theme = "blue";

		} else if (((prefs.getString("theme", "a")).charAt(0) == 'c')) {
			setTheme(R.style.AppThemeOrange);
			setContentView(R.layout.mainorange);
			theme = "orange";

		} else if (((prefs.getString("theme", "a")).charAt(0) == 'd')) {
			setTheme(R.style.AppThemePurple);
			setContentView(R.layout.mainpurple);
			theme = "purple";

		} else if (((prefs.getString("theme", "a")).charAt(0) == 'e')) {
			setTheme(R.style.AppThemeRed);
			setContentView(R.layout.mainred);
			theme = "red";

		} else {
			setContentView(R.layout.main);
			theme = "standard";
		}
		//System.out.println(prefs.getString("theme", "lightgreen"));
		super.onCreate(savedInstanceState);
		
		if (angle_settings.charAt(0) == 'a') {
			angle_type = "degrees";
		} 
		else if (angle_settings.charAt(0) == 'b') {
			angle_type = "radians";
		}

		// ActionBar.setIcon(R.drawable.pushed);

		editText = (EditText) findViewById(R.id.editText1);

		slidingdrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer1);

		linearlayout1 = (LinearLayout) findViewById(R.id.linearLayout1);

		button0 = (Button) findViewById(R.id.button0);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);

		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		button7 = (Button) findViewById(R.id.button7);
		button8 = (Button) findViewById(R.id.button8);
		button9 = (Button) findViewById(R.id.button9);

		buttonPlus = (Button) findViewById(R.id.buttonPlus);
		buttonMinus = (Button) findViewById(R.id.buttonMinus);
		buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
		buttonDivide = (Button) findViewById(R.id.buttonDivide);
		buttonPoint = (Button) findViewById(R.id.buttonPoint);

		buttonEqual = (Button) findViewById(R.id.buttonEqual);

		button_sin = (Button) findViewById(R.id.button_sin);
		button_cos = (Button) findViewById(R.id.button_cos);
		button_tan = (Button) findViewById(R.id.button_tan);
		button_root = (Button) findViewById(R.id.button_root);
		/*
		 * button_squared_2 = (Button) findViewById(R.id.button_squared_2);
		 * 
		 * button_del = (Button) findViewById(R.id.button_del); button_dec =
		 * (Button) findViewById(R.id.button_dec); button_bin = (Button)
		 * findViewById(R.id.button_bin);
		 */
		buttonReset = (Button) findViewById(R.id.buttonReset);

		// button_del=(Button)findViewById(R.id.button_del);

		editText.setText(result.toString());

		EditText result = (EditText) findViewById(R.id.editText1);
		// Getting fonts
		Typeface light_font = Typeface.createFromAsset(getAssets(),
				"Roboto-Light.ttf");
		Typeface thin_font = Typeface.createFromAsset(getAssets(),
				"Roboto-Thin.ttf");
		Typeface medium_font = Typeface.createFromAsset(getAssets(),
				"Roboto-Medium.ttf");
		Typeface condensedbold_font = Typeface.createFromAsset(getAssets(),
				"Roboto-BoldCondensed.ttf");

		// Setting fonts
		result.setTypeface(light_font);
		buttonEqual.setTypeface(thin_font);
		button0.setTypeface(thin_font);
		button1.setTypeface(thin_font);
		button2.setTypeface(thin_font);
		button3.setTypeface(thin_font);
		button4.setTypeface(thin_font);
		button5.setTypeface(thin_font);
		button6.setTypeface(thin_font);
		button7.setTypeface(thin_font);
		button8.setTypeface(thin_font);
		button9.setTypeface(thin_font);
		buttonMinus.setTypeface(thin_font);
		buttonMultiply.setTypeface(thin_font);
		buttonDivide.setTypeface(thin_font);
		buttonPoint.setTypeface(thin_font);
		buttonPlus.setTypeface(thin_font);
		buttonReset.setTypeface(condensedbold_font);

		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		editText.setSelection(editText.getText().length());
		
		editText.setOnLongClickListener(new OnLongClickListener() { 
	        @Override
	        public boolean onLongClick(View v) {
	        	InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	    		mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	            return true;
	        }
	    });
		
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = this.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			if (colored_notification_bar) {
				int color = -1;
				if (theme == "blue")
					color = R.color.statusbarblue;
				else if (theme == "orange")
					color = R.color.statusbarorange;
				else if (theme == "purple")
					color = R.color.statusbarpurple;
				else if (theme == "red")
					color = R.color.statusbarred;
				else
					color = R.color.statusbar;
				window.setStatusBarColor(this.getResources().getColor(color));
			}
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
					&& colored_navigation_bar) {
				int color = -1;
				if (theme == "blue")
					color = R.color.navigationbarblue;
				else if (theme == "orange")
					color = R.color.navigationbarorange;
				else if (theme == "purple")
					color = R.color.navigationbarpurple;
				else if (theme == "red")
					color = R.color.navigationbarred;
				else
					color = R.color.navigationbar;
				window.setNavigationBarColor(this.getResources().getColor(color));	    
			}
	    }
	}

	// public void onClickListenerView(View v) {
	// slidingdrawer.animateClose();
	// en}

	public void addText(String text, Boolean function, Boolean operator) {
		int length = text.length();
		if (!no_calc) {
			calc = editText.getText().toString();
		}
		no_calc = false;
		
		if (calc.length() == 0) {
			if (!function) {
				calc += text;
				editText.setText(calc);
				editText.setSelection(length);
			} else {
				calc = calc + text + ")";
				editText.setText(calc);
				editText.setSelection(length);
			}
		} else {
			if (press != '=') {
				if (editText.getSelectionStart() == editText.getSelectionEnd()) {
					cursor = editText.getSelectionStart();
					if (!function) {
						calc = calc.substring(0, cursor) + text + calc.substring(cursor);
						editText.setText(calc);
						if (cursor == (editText.getText().length() - 1)) {
							editText.setSelection(editText.getText().length());
						} else {
							editText.setSelection(cursor + length);
						}
					} 
					else {
						calc = calc.substring(0, cursor) + text + ")" + calc.substring(cursor);
						editText.setText(calc);
						editText.setSelection(cursor + length);
					}
				}
				else {
					int begin = editText.getSelectionStart();
					int end = editText.getSelectionEnd();
					if (!function) {
						calc = calc.substring(0, begin) + text + calc.substring(end);
						editText.setText(calc);
						editText.setSelection(begin + length);
					} else {
						calc = calc.substring(0, begin) + text + ")" + calc.substring(end);
						editText.setText(calc);
						editText.setSelection(begin + length);
					}
				}
			
			}
			else if (press =='=') {
				press = ' ';
				cursor = editText.getSelectionStart();
				if (cursor == calc.length() && editText.getSelectionStart() == editText.getSelectionEnd() ) {
					if (!function) {
						if (!operator) {
							calc = text;
							editText.setText(calc);
							editText.setSelection(length);
						} else {
							calc = calc + text;
							editText.setText(calc);
							editText.setSelection(calc.length());
						}
					} else {
						int result_length = calc.length();
						calc = text + calc + ")";
						editText.setText(calc);
						editText.setSelection(length + result_length);
					}
				}
				else {
					if (editText.getSelectionStart() == editText.getSelectionEnd()) {
						cursor = editText.getSelectionStart();
						if (!function) {
							calc = calc.substring(0, cursor) + text + calc.substring(cursor);
							editText.setText(calc);
							if (cursor == (editText.getText().length() - 1)) {
								editText.setSelection(editText.getText().length());
							} else {
								editText.setSelection(cursor + length);
							}
						} 
						else {
							calc = calc.substring(0, cursor) + text + ")" + calc.substring(cursor);
							editText.setText(calc);
							editText.setSelection(cursor + length);
						}
					}
					else {
						int begin = editText.getSelectionStart();
						int end = editText.getSelectionEnd();
						if (!function) {
							calc = calc.substring(0, begin) + text + calc.substring(end);
							editText.setText(calc);
							editText.setSelection(begin + length);
						} else {
							calc = calc.substring(0, begin) + text + ")" + calc.substring(end);
							editText.setText(calc);
							editText.setSelection(begin + length);
						}
					}
				}
			}
		}
		
	}

	public void onClickListener0(View v) {
		addText("0", false, false);
	}

	public void onClickListener1(View v) {
		addText("1", false, false);
	}

	public void onClickListener2(View v) {
		addText("2", false, false);
	}

	public void onClickListener3(View v) {
		addText("3", false, false);
	}

	public void onClickListener4(View v) {

		addText("4", false, false);
	}

	public void onClickListener5(View v) {
		addText("5", false, false);
	}

	public void onClickListener6(View v) {
		addText("6", false, false);
	}

	public void onClickListener7(View v) {
		addText("7", false, false);
	}

	public void onClickListener8(View v) {
		addText("8", false, false);
	}

	public void onClickListener9(View v) {
		addText("9", false, false);
	}

	public void onClickListenerPlus(View v) {
		press = '+';
		addText("+", false, true);
	}

	public void onClickListenerMinus(View v) {
		press = '-';
		addText("-", false, true);
	}

	public void onClickListenerMultiply(View v) {
		press = '*';
		addText("*", false, true);
	}

	public void onClickListenerDivide(View v) {
		press = '/';
		addText("/", false, true);
	}

	public void onClickListenerPoint(View v) {

		int error = 0;

		if (sum != null) {
			for (int i = 0; i < sum.length(); i++) {
				if (sum.charAt(i) == '.') {
					error = 1;
					Context context = getApplicationContext();
					CharSequence text = "Only one point allowed.";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					break;
				}
			}

		}

		if (error == 0) {
			if (sum == null) {
				calc += "0.1";
			} else {
				addText(".", false, false);
			}
		}

	}

	public void onClickListenerEqual(View v) {
		calc = editText.getText().toString();
		try {
			Expression e = new Expression(calc);
			
			e = e.setPrecision(0);
			
			e.addOperator(e.new Operator("/", 30, true) {
				@Override
				public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
					MathContext mc = new MathContext(precision);
					BigDecimal answer = v1.divide(v2, mc);
					return answer;
				}
			});
			e.addFunction(e.new Function("asin", 1) {
			    @Override
			    public BigDecimal eval(List<BigDecimal> parameters) {
			    	BigDecimal parameter = parameters.get(0);
			    	String parameter_str = parameter.toString();
			    	double parameter_dbl =  Double.parseDouble(parameter_str);
			    	double answer = Math.asin(parameter_dbl);
			    	double answer_final;
			    	if (angle_type == "radians") {
			    		answer_final = answer;
			    	}
			    	else {
			    		answer_final = Math.toDegrees(answer);
			    	}
			    	BigDecimal answer_bd = new BigDecimal(answer_final);
			    	answer_bd =  answer_bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
			    	return answer_bd;  
			    }
			});
			e.addFunction(e.new Function("acos", 1) {
			    @Override
			    public BigDecimal eval(List<BigDecimal> parameters) {
			    	BigDecimal parameter = parameters.get(0);
			    	String parameter_str = parameter.toString();
			    	double parameter_dbl =  Double.parseDouble(parameter_str);
			    	double answer = Math.acos(parameter_dbl);
			    	double answer_final;
			    	if (angle_type == "radians") {
			    		answer_final = answer;
			    	}
			    	else {
			    		answer_final = Math.toDegrees(answer);
			    	}
			    	BigDecimal answer_bd = new BigDecimal(answer_final);
			    	answer_bd =  answer_bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
			    	return answer_bd; 
			    }
			});
			e.addFunction(e.new Function("atan", 1) {
			    @Override
			    public BigDecimal eval(List<BigDecimal> parameters) {
			    	BigDecimal parameter = parameters.get(0);
			    	String parameter_str = parameter.toString();
			    	double parameter_dbl =  Double.parseDouble(parameter_str);
			    	double answer = Math.atan(parameter_dbl);
			    	double answer_final;
			    	if (angle_type == "radians") {
			    		answer_final = answer;
			    	}
			    	else {
			    		answer_final = Math.toDegrees(answer);
			    	}
			    	BigDecimal answer_bd = new BigDecimal(answer_final);
			    	answer_bd =  answer_bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
			    	return answer_bd;  
			    }
			});
			e.addFunction(e.new Function("sin", 1) {
			    @Override
			    public BigDecimal eval(List<BigDecimal> parameters) {
			    	BigDecimal parameter = parameters.get(0);
			    	String parameter_str = parameter.toString();
			    	double parameter_dbl =  Double.parseDouble(parameter_str);
			    	double parameter_final;
			    	System.out.println(angle_settings);
			    	if (angle_type == "radians") {
			    		parameter_final = parameter_dbl;
			    	}
			    	else {
			    		parameter_final = Math.toRadians(parameter_dbl);
			    	}
			    	double answer = Math.sin(parameter_final);
			    	System.out.println(answer);
			    	BigDecimal answer_bd = new BigDecimal(answer);
			    	answer_bd =  answer_bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
			    	return answer_bd;           
			    }
			});
			e.addFunction(e.new Function("cos", 1) {
			    @Override
			    public BigDecimal eval(List<BigDecimal> parameters) {
			    	BigDecimal parameter = parameters.get(0);
			    	String parameter_str = parameter.toString();
			    	double parameter_dbl =  Double.parseDouble(parameter_str);
			    	double parameter_final;
			    	if (angle_type == "radians") {
			    		parameter_final = parameter_dbl;
			    	}
			    	else {
			    		parameter_final = Math.toRadians(parameter_dbl);
			    	}
			    	double answer = Math.cos(parameter_final);
			    	System.out.println(answer);
			    	BigDecimal answer_bd = new BigDecimal(answer);
			    	answer_bd =  answer_bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
			    	return answer_bd;            
			    }
			});
			e.addFunction(e.new Function("tan", 1) {
			    @Override
			    public BigDecimal eval(List<BigDecimal> parameters) {
			    	BigDecimal parameter = parameters.get(0);
			    	String parameter_str = parameter.toString();
			    	double parameter_dbl =  Double.parseDouble(parameter_str);
			    	double parameter_final;
			    	if (angle_type == "radians") {
			    		parameter_final = parameter_dbl;
			    	}
			    	else {
			    		parameter_final = Math.toRadians(parameter_dbl);
			    	}
			    	double answer = Math.tan(parameter_final);
			    	System.out.println(answer);
			    	BigDecimal answer_bd = new BigDecimal(answer);
			    	answer_bd =  answer_bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
			    	return answer_bd;      
			    }
			});
			e.addFunction(e.new Function("SQRT", 1) {
				@Override
				public BigDecimal eval(List<BigDecimal> parameters) {
					/*
					 * From The Java Programmers Guide To numerical Computing
					 * (Ronald Mak, 2003)
					 */
					BigDecimal x = parameters.get(0);
					if (x.compareTo(BigDecimal.ZERO) == 0) {
						return new BigDecimal(0);
					}
					BigInteger n = x.movePointRight(precision << 1)
							.toBigInteger();

					int bits = (n.bitLength() + 1) >> 1;
					BigInteger ix = n.shiftRight(bits);
					BigInteger ixPrev;

					do {
						ixPrev = ix;
						ix = ix.add(n.divide(ix)).shiftRight(1);
						// Give other threads a chance to work;
						Thread.yield();
					} while (ix.compareTo(ixPrev) != 0);

					return new BigDecimal(ix, precision);
				}
			});
			BigDecimal result_bd = e.eval();
			
			String tmp_result = result_bd.toPlainString();
			editText.setText(tmp_result);
			calc = editText.getText().toString();
			no_calc = false;
			press = '=';
			editText.setSelection(editText.getText().length());
		} catch (Exception err) {
			Context context = getApplicationContext();
			CharSequence text = "Input error.";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			// TODO Here is equal
		}
	}

	public void onClickListener_sin(View v) {
		addText("sin(", true, false);
	}

	public void onClickListener_cos(View v) {
		addText("cos(", true, false);
	}

	public void onClickListener_tan(View v) {
		addText("tan(", true, false);
	}

	public void onClickListener_asin(View v) {
		addText("asin(", true, false);
	}

	public void onClickListener_acos(View v) {
		addText("acos(", true, false);
	}

	public void onClickListener_atan(View v) {
		addText("atan(", true, false);
	}

	public void onClickListener_squared_2(View v) {
		addText("^2", false, true);
	}

	public void onClickListener_exp(View v) {
		addText("^", false, true);
	}

	public void onClickListener_root(View v) {
		addText("sqrt(", true, false);
	}

	public void onClickListener_openpar(View v) {
		addText("(", false, false);
	}

	public void onClickListener_closepar(View v) {
		addText(")", false, false);
	}

	public void onClickListener_mod(View v) {
		addText("%", false, true);
	}

	public void onClickListener_pi(View v) {
		addText("PI", false, false);
	}

	public void onClickListener_log(View v){
		addText("log(",false,false);
	}
	public void onClickListener_ln(View v){
		addText("ln(",false,false);
	}
	public void onClickListener_e(View v){
		addText("e",false,false);
	}
	public void onClickListener_del(View v) {
		System.out.println("ja");
		if (editText.getSelectionStart() > 0 | editText.getSelectionEnd() > 0) {
			
			
			if (editText.getSelectionStart() == editText.getSelectionEnd()) {
				if (calc.length() !=0) {
					cursor = editText.getSelectionStart();
					calc = calc.substring(0, (cursor - 1)) + calc.substring(cursor);
					editText.setText(calc);
					if (cursor == (editText.getText().length() + 1)) {
						editText.setSelection(editText.getText().length());
					} else {
						editText.setSelection(cursor - 1);
					}
					System.out.println("ksja");
				} else {
					
					editText.setText(calc);
				}
			}
			else {
				int begin = editText.getSelectionStart();
				int end = editText.getSelectionEnd();
				calc = calc.substring(0, begin) + calc.substring(end);
				editText.setText(calc);
				editText.setSelection(begin);
			}
			
		}
	}

	public void onClickListenerReset(View v) {
		sum = "";
		countOne = 0;

		result = 0f;
		result_mul = 1f;
		result_div = 1f;
		press = ' ';
		c = 0;

		editText.setText(result.toString());
		editText.setSelection(editText.getText().length());
		no_calc = true;
		calc = "";
	}

	public void onClickListener_dec(View v) {
		addText("dec(", true, false);
	}

	public void onClickListener_bin(View v) {
		addText("bin(", true, false);
	}

	public void onClickListener_hex(View v) {
		addText("hex(", true, false);
	}

	public void onClickListener_oct(View v) {
		addText("oct(", true, false);
	}


	public void onClickListener_input(View v) {
		InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}
}
