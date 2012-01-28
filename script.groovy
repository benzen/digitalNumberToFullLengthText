def normalize = {s -> s.trim().replaceAll(/\s+/," ")}
def countUnits     = { number -> number %10 as int}
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

def convert = { number ->
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

assert countUnits(31) == 1
assert countTens(432) == 3
assert countHundreds(54334) == 3
assert countThousands(543534543) == 534 
assert countMillions(543534543) == 543
assert countBillions(987543534543) == 987 


assert convert(1)            == "un"
assert convert(16)           == "seize"
assert convert(18)           == "dix huit"
assert convert(21)           == "vingt et un"
assert convert(72)           == "soixante douze"
assert convert(71)           == "soixante et onze "
assert convert(165)          == "cent soixante cinq"
assert convert(345)          == "trois cent quarante cinq"
assert convert(805)          == "huit cent cinq" 
assert convert(1254)         == "mille deux cent cinquante quatre"
assert convert(3762)         == "trois mille sept cent soixante deux"
assert convert(1430955)      == "un million quatre cent trente mille neuf cent cinquante cinq"
assert convert(1000000000)   == "un milliard"
assert convert(13428975438) == "treize milliard quatre cent vingt huit million neuf cent soixante quinze mille quatre cent trente huit" 
