package org.code3.numberConverter

class FrenchNumberToTextConverter implements NumberToTextConverter{
	
def normalize = {s -> s.trim().replaceAll(/\s+/," ")}
def countUnits = { number -> number %10 as int}
def countTens      = { number -> ( ( number/10 )as int ) % 10  as int}
def countHundreds  = { number -> ( ( number/100 ) as int ) % 10 as int}

def countThousands = { number -> ( ( number/1000 ) as int ) % 1000 as int}
def countMillions  = { number -> ( ( number/1000000 ) as int ) % 1000 as int}
def countBillions  = { number -> ( ( number/1000000000 ) as int ) % 1000 as int}

def numberToWord =  [ 0:"zero", 1:"un", 2:"deux", 3:"trois", 4:"quatre", 5:"cinq", 6:"six", 7:"sept", 8:"huit", 9:"neuf",                                         			
					  10:"dix",11:"onze",12:"douze",13:"treize",14:"quatorze",15:"quinze",16:"seize",
                      20:"vingt",30:"trente",40:"quarante",50:"cinquante",60:"soixante", 
                      70:"soixante dix", 71:"soixante et onze",72:"soixante douze",73:"soixante treize",74:"soixante quatorze",
                      75:"soixante quinze",76:"soixante seize",77:"soixante dix sept",78:"soixante dix huit",79:"soixante dix neuf",80:"quatre vingt",
                      90:"quatre vingt-dix",91:"quatre vingt onze",92:"quatre vingt douze",93:"quatre vint treize",94:"quatre vingt quatorze",
                      95:"quatre vingt quinze",96:"quatre vingt seize",97:"quatre vingt dix sept",98:"quatre vingt dix huit",99:"quatre vingt dix neuf", 100:"cent"]
def upperNumberToWord = [1000:"mille",1000000:"million", 1000000000:"milliard"]

def numberToString = {counted, multiple = null->
    if( counted == numberToWord.get(0)) return ""
    if( (counted == numberToWord.get(1))  && ( multiple == numberToWord.get(100) || multiple == upperNumberToWord.get(1000))) return multiple 
    if( multiple && counted) return "$counted  $multiple"
    return  counted
}
	
def numberToWordToString = { number, multiple =null -> numberToString( numberToWord.get( number ), multiple ) }

def convertUpToThousand = { number ->
    def hundreds           = numberToWordToString( countHundreds(number),  numberToWord.get(100) )
    def oneWordTensAndUnit = numberToWordToString( countTens( number ) * 10 + countUnits( number ) )
    def units              = numberToWordToString( countUnits(number) )
    def tens               = numberToWordToString( countTens(number) * 10)    
    def optionalAnd        = ( (units == numberToWord.get(1)) && (tens) )?"et":""
    def tensAndUnits       = oneWordTensAndUnit?:"$tens $optionalAnd $units"
    normalize("$hundreds $tensAndUnits")
}

String convert (Integer number){ 
    def builder = new StringBuilder()
    def billions = countBillions(number)
    def bullionAsString = numberToString(convertUpToThousand(billions),upperNumberToWord.get(1000000000))
    
    def millions = countMillions(number)
    def millionsAsString = numberToString(convertUpToThousand(millions), upperNumberToWord.get(1000000))

    def thousands = countThousands(number) 
    def thousandsAsString = numberToString( convertUpToThousand(thousands), upperNumberToWord.get(1000) )
    
    builder.append(bullionAsString)
    builder.append(" ")
    builder.append(millionsAsString)
    builder.append(" ")
    builder.append( thousandsAsString)
    builder.append( " " )
    
    builder.append(convertUpToThousand(number))
    
    normalize(builder.toString())
}


}
