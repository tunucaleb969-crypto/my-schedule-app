package com.schedule.app

data class QuizQuestion(val question: String, val answer: String)

object QuizData {
    val questions = mapOf(
        "Analog Electronics" to listOf(
            QuizQuestion("What does a diode do?", "Allows current to flow in one direction only"),
            QuizQuestion("What is the unit of resistance?", "Ohm"),
            QuizQuestion("What does BJT stand for?", "Bipolar Junction Transistor"),
            QuizQuestion("What are the three terminals of a BJT?", "Base, Collector, Emitter"),
            QuizQuestion("What does an NPN transistor's arrow direction indicate?", "Current flows out of the emitter"),
            QuizQuestion("What is Ohm's Law?", "V = I × R"),
            QuizQuestion("What is a rectifier used for?", "Converting AC to DC"),
            QuizQuestion("What does FET stand for?", "Field Effect Transistor"),
            QuizQuestion("What is the function of a capacitor?", "Stores electrical energy in an electric field"),
            QuizQuestion("What is gain in an amplifier?", "The ratio of output signal to input signal")
        ),
        "Calculus with Differential Equations" to listOf(
            QuizQuestion("What is the derivative of x^2?", "2x"),
            QuizQuestion("What is the integral of 1/x?", "ln|x| + C"),
            QuizQuestion("What order is a differential equation with a second derivative?", "Second order"),
            QuizQuestion("What is the derivative of sin(x)?", "cos(x)"),
            QuizQuestion("What is the derivative of a constant?", "Zero"),
            QuizQuestion("What does 'homogeneous' mean for a differential equation?", "Equal to zero on the right-hand side"),
            QuizQuestion("What is the integral of cos(x)?", "sin(x) + C"),
            QuizQuestion("What rule is used to differentiate a product of two functions?", "Product rule"),
            QuizQuestion("What is the derivative of e^x?", "e^x"),
            QuizQuestion("What method solves separable differential equations?", "Separation of variables")
        ),
        "Electromagnetic Field Theory" to listOf(
            QuizQuestion("What is the unit of electric field strength?", "Volts per meter (V/m)"),
            QuizQuestion("What law relates electric flux to enclosed charge?", "Gauss's Law"),
            QuizQuestion("What is the SI unit of magnetic flux?", "Weber (Wb)"),
            QuizQuestion("What is the SI unit of electric charge?", "Coulomb"),
            QuizQuestion("What does Faraday's Law describe?", "Induced EMF from a changing magnetic field"),
            QuizQuestion("What is permittivity a measure of?", "How an electric field affects, and is affected by, a medium"),
            QuizQuestion("What is the symbol for permeability?", "μ (mu)"),
            QuizQuestion("What does Coulomb's Law describe?", "The force between two point charges"),
            QuizQuestion("What is the unit of magnetic field strength (H)?", "Amperes per meter (A/m)"),
            QuizQuestion("What law states that magnetic field lines form closed loops?", "Gauss's Law for Magnetism")
        ),
        "Electrical Machines Theory" to listOf(
            QuizQuestion("What does EMF stand for?", "Electromotive Force"),
            QuizQuestion("What is a transformer used for?", "Stepping voltage up or down via mutual induction"),
            QuizQuestion("What is 'slip' in an induction motor?", "The difference between synchronous speed and rotor speed"),
            QuizQuestion("What type of motor runs at constant speed matching supply frequency?", "Synchronous motor"),
            QuizQuestion("What is the main function of a commutator in a DC machine?", "Converts AC induced in the armature to DC output"),
            QuizQuestion("What law explains how a generator produces EMF?", "Faraday's Law of electromagnetic induction"),
            QuizQuestion("What are the two main parts of a rotating electrical machine?", "Stator and rotor"),
            QuizQuestion("What is the purpose of a transformer core?", "To provide a low-reluctance path for magnetic flux"),
            QuizQuestion("What does 'synchronous speed' depend on?", "Supply frequency and number of poles"),
            QuizQuestion("What is back EMF in a DC motor?", "The EMF induced that opposes the supply voltage")
        ),
        "Mechanics of Fluid" to listOf(
            QuizQuestion("What is Bernoulli's principle about?", "Relationship between pressure, velocity, and elevation in a moving fluid"),
            QuizQuestion("What is viscosity?", "A fluid's resistance to flow"),
            QuizQuestion("What is the SI unit of pressure?", "Pascal (Pa)"),
            QuizQuestion("What does 'incompressible flow' mean?", "Fluid density remains constant"),
            QuizQuestion("What is the continuity equation based on?", "Conservation of mass"),
            QuizQuestion("What is a Newtonian fluid?", "A fluid whose viscosity is constant regardless of shear rate"),
            QuizQuestion("What is buoyancy caused by?", "Difference in pressure between the top and bottom of a submerged object"),
            QuizQuestion("What is the Reynolds number used to predict?", "Whether flow is laminar or turbulent"),
            QuizQuestion("What is specific gravity?", "The ratio of a substance's density to the density of water"),
            QuizQuestion("What is hydrostatic pressure?", "Pressure exerted by a fluid at rest due to gravity")
        )
    )
}
