package org.code3.numberConverter
class DefaultNumberToTextConverter{
	/**
	* Converter a number to it's text full length representation according to the given locale
	**/
	def convert(Locale locale, Integer number){
		if(locale.language == Locale.FRENCH){
			return FrenchNumberToTextConverter.convert(number)
		}
	}
}
