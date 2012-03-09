packageorg.code3.numberConverter.test 
import org.code3.numberConverter.FrenchNumberConverter

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
