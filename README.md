# SolarSystem

Fisierul de intrare:
	-prima linie va avea:
		<timpul de referinta al configurarii - T0> <timpul target al configurarii -T1>
		
	-pe urmatoarele linii, urmatorul tip de input:
		"<planetName> <x-coordinate> <y-coordinate> <radius> <mass>"
		
	-dupa ce au fost introduse toate planetele cu coordonatele lor, urmatoarele linii
	vor arata in felul urmator:
		"<planetName(the satelite/child)> <planetName(the origin/parent)> <movementType>"
		
	-Legenda:
	planetName = numele planetei (tip de data: string)
	x-coordinate = coordonata x (tip de data: double)
	y-coordinate = coordonata y (tip de data: double)
	radius = raza planetei (tip de data: double, dat in metri)
	mass = masa planetei (tip de data: double, dat in kilograme)
	movementType = clockwise/counterclockwise (tip de data: string)
	timpul de referinta al configurarii = timpul pentru care coordnatele introduse sunt cunoscute
	timpul target al configurarii = timpul pentru care avem de calculat coordonatele corpurilor

 

Functionalitatea java8 se gaseste in: console.UIConsole.run()