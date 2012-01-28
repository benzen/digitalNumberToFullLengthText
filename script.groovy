def countUnits = {number -> number %10}
def countTens = {number -> ((number/10)as int) % 10}
def countHundreds = { number -> ((number/100) as int)%10}
def countThousands ={number -> ((number/1000) as int) % 1000}
def countMillions = {number -> ((number/1000000)as int )% 1000}
def countBillions = {number -> ((number/1000000000)as int )% 1000}

def numberToWord =  [ 0:"zero", 1:"un", 2:"deux", 3:"trois", 4:"quatre", 5:"cinq", 6:"six", 7:"sept", 8:"huit", 9:"neuf",                                         			
					  10:"dix",11:"onze",12:"douze",13:"treize",14:"quatorze",15:"quinze",16:"seize",
                      20:"vingt",30:"trente",40:"quarante",50:"cinquante",60:"soixante", 70:"soixante-dix",80:"quatre-vingt",90:"quatre-vingt-dix", 100:"cent"]
def upperNumberToWord = [1000:"mille",1000000:"million"]

def numberToString = {counted, multiple = null->
    if(!counted) return ""
    if( multiple  &&  (counted == 1) ) return multiple 
    if(multiple && counted) return "${numberToWord.get(counted)} $multiple"
    return  numberToWord.get(counted)
}
def coumpondNumberToString = { counted, multiple = null->
    if(!counted) return ""
    if( multiple  && (multiple != upperNumberToWord.get(1000)) && ( counted == numberToWord.get(1) ) ) return multiple 
    if(multiple && counted) return "$counted $multiple"
    return  numberToWord.get(counted)
}

def convertUpToThousand = { number ->
    def hundreds = numberToString( countHundreds(number),  numberToWord.get(100) )
    def oneWordTensAndUnit = numberToString(countTens(number)*10+countUnits(number))
    def units = numberToString(countUnits(number))
    def tens = numberToString(countTens(number)*10)    
    def optionalAnd = ( (units == numberToWord.get(1)) && (tens) )?"et":""
    def tensAndUnits = oneWordTensAndUnit?:"$tens $optionalAnd $units"
    "$hundreds $tensAndUnits".trim().replaceAll(/\s+/," ")
}
def convert = { number ->
    def builder = new StringBuilder()
    def millions = countMillions(number)
    def millionsAsString = numberStringToString(convertUpToThousand(millions), upperNumberToWord.get(1000000))

    def thousands = countThousands(number) 
    def thousandsAsString = numberStringToString( convertUpToThousand(thousands), upperNumberToWord.get(1000) )
    
    builder.append(millionsAsString)
    builder.append(" ")
    builder.append( thousandsAsString)
    builder.append( " " )
    builder.append(convertUpToThousand(number))
    builder.toString().trim().replaceAll(/\s+/," ")
   
}

assert countUnits(31) == 1
assert countTens(432) == 3
assert countHundreds(54334) == 3
assert countThousands(543534543) == 534 
assert countMillions(543534543) == 543
assert countBillions(987543534543) == 987 

assert convert(1) == "un"
assert convert(21) == "vingt et un"
assert convert(345) == "trois cent quarante cinq"
assert convert(805) == "huit cent cinq" 
assert convert(16) == "seize"
assert convert(18) == "dix huit"
assert convert(165) == "cent soixante cinq"
assert convert(1254) == "mille deux cent cinquante quatre"
assert convert(3762) == "trois mille sept cent soixante deux"
assert convert(1430955) == "un million quatre cent trente mille neuf cent cinquante cinq"

​

