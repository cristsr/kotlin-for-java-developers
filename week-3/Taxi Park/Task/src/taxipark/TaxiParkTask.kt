package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    allDrivers.filter { trips.none { trip -> it.name == trip.driver.name } }.toSet()


/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    allPassengers
        .filter { trips.count { trip -> trip.passengers.any { passenger -> it.name == passenger.name } } >= minTrips }
        .toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    trips
        .asSequence()
        .filter { it.driver.name == driver.name }
        .map { it.passengers }
        .flatten()
        .groupBy { it.name }
        .filter { it.value.size > 1 }
        .map { (_, v) -> v.first() }
        .toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    allPassengers
        .asSequence()
        .map { psgr -> psgr to trips.filter { trips -> trips.passengers.any { it.name == psgr.name } } }
        .map { (psgr, trips) -> psgr to trips.partition { (it.discount ?: 0.0) > 0.0 } }
        .filter { (_, trips) -> trips.first.size > trips.second.size }
        .map { it.first }
        .toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    // Get the range of the numbers given the parameter
    val getRange: (Int) -> IntRange = {
        val start = (it / 10) * 10
        start.. start + 9
    }

    return trips
        .groupBy { getRange(it.duration) }
        .maxByOrNull { it.value.size }
        ?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    TODO()
}
