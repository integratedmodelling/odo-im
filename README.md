# Ontology of Descriptions and Observations for Integrated Modelling (ODO-IM)


<img src="https://docs.integratedmodelling.org/klab/_images/KLAB_LOGO.png" align="right"
     alt="k.LAB logo" width="600">
     

- **Authors**: [Integrated Modelling Partnership](http://www.integratedmodelling.org); Ferdinando Villa, Ph.D.; Greta Adamo, Ph.D.
- **License**: [CC-BY v4.0](http://creativecommons.org/licenses/by/4.0/)
- [**Bug reports and feature requests**](https://github.com/integratedmodelling/odo-im/issues)

The ODO-IM ontology is a core product developed and maintained by the [Integrated Modelling Partnership](http://www.integratedmodelling.org) (IMP) as the core observation ontology used in the k.LAB software stack. It specifies a view of the scientific process that can be implemented in software to support the IMP's goals of modular, distributed, semantically explicit and integrated scientific computing and modeling, according to the FAIR principles. ODO-IM provides five main core concepts:

- **Description**s describe the activities that produce scientific artifacts  describing a concept. Subclasses of Description can be used to describe scientific assertions, models and computational workflows (Definitions, see below).
- **Observation**s describe the scientific artifacts themselves, resulting from instantiating a Description activity within the context of an acknowledged root observation. Each concrete Description subclass is restricted to produce a specific type of Observation.
- The **Observable** hierarchy describes the different concepts that serve as the object of a Description and provide the basic semantics for the resulting Observation. Observables can be combined with **Predicate**s (such as attributes, roles etc) using restrictions on ODO-IM properties. The ODO-IM adopts an *orthogonality* principle that implies that predicates are never implicit and are mixed with observables through explicit restrictions. In k.LAB, these restrictions are created through the parsing of a logical expression stated in a user-friendly language (k.IM), so that users do not directly restrict OWL concepts. 

Observables are classified along two main, dychotomic logical dimensions:
    + Continuant vs. Occurrent nature, i.e. whether or not time is integral to the phenomenological nature of the observable;
    + Countability, i.e. the possibility of an observation of the observable to describe multiple instances of the observable.

Together, these delineate the six main categories of **observables**:

| | Non-countable | Countable | |
| --- | --- | --- | --- |
| Continuant | Quality | Subject | Structural relationship |
| Occurrent  | Process | Event | Functional relationship |

These observables are further articulated in ODO-IM to provide a solid foundation for any ontology of scientific observations.

- The main logical dimensions for **predicates** are:
    + Identities
    + Realms
    + Roles
    + Orderings
    + Attributes (which are not any of the above)

In addition, _domains_, _extents_ and _configurations_ complete the first-level hierarchy of predicates and observables.

- **Definition**s are logical structures that can be used by an intelligent agent to carry out the Description of a specified Observable. Definitions specify observations either by acknowledgement (through an extensional statement) or by producing a computable dataflow (intensional statements listing zero or more Observables as dependencies). In ODO-IM applications, this class includes the main products of the activity of semantic modelling, i.e. what is commonly referred to as "data annotations" and "models". Properties and restrictions constrain specific classes of Definitions to specific classes of Observables.

ODO-IM does _not_ cover several topics of common occurrence in other observation ontologies:

- It remains as agnostic as possible about the phenomenology of Observables, in an effort to preserve maximum orthogonality with an arbitrary upper ontology stated in a derived worldview. The characterization of Observables is intentionally shallow, as observables are meant to specialize types in an externally provided upper ontology to form the basis of a _worldview_. Such links are made in the root domain of the worldview. See the full documentation for minimum expressive requirements and caveats.
- It does not provide any detail on space, time and any other topologies implied in the definition of observational scale, beyond their statement as extentual observables (Extent), as their specific interpretation is left to worldviews derived from ODO-IM.
- It does not provide semantics for units of measurement, like other observation ontologies do. In ODO-IM units do not hold the same ontological dignity as the remaining subject matter. The applications where ODO-IM is used do not need units as stated instances and do not use reasoning to validate units - an endeavor that would, anyway, require second-order logics in order to handle the transformation of units through aggregation and propagation in time and space when shifts in observation semantics cause the context topology to collapse or subdivide, without altering the observable semantics. To support applications, ODO-IM does lists the SI base unit textually in annotation properties linked to all PhysicalProperty observables, using SI conventions. Applications can use a unit parser to translate these into data structures for validation and conversion.

## Dependencies

ODO-IM only depends on the [PROV-O](https://www.w3.org/TR/prov-o/) provenance ontology, as Descriptions and Observations are, respectively, Activities (prov:Activity) and informational artifacts (prov:Entity).

## Rationale and Usage

The ODO-IM ontology is released under CC-BY license for any purpose. Its main rationale for existence is to serve as the semantic backbone of the *semantic meta-modeling* paradigm maintained by the IMP and operationalized through the k.LAB software stack. As part of this strategy, the k.IM language incarnates ODO-IM axioms in the syntax of a user-friendly language,  for which k.LAB provides intelligent editor support. The k.IM language allows its users to: 

- Define *worldviews* by specifying and composing Observables and Predicates, through efficient, flexible and readable statements, optimized for multi-domain, multi-scale definitions and with a built-in mechanisms for safe linking to existing controlled vocabularies. 
- Specify interoperable data annotations and computations (Definitions). The k.LAB software stack provides the infrastructure to publish such definitions, along with the resources they depend on, on networked repositories managed by specialized servers, and to peruse a federation of such servers to assemble context-specific computations that are run to generate Observations based on a user query for the corresponding Observable. 
	
Links to an upper ontology of choice can be made in k.IM, in the root domain of each worldview, and are meant to support reasoning outside of k.LAB as the reasoning in k.LAB only depends on ODO-IM concepts. Linking to an upper ontology is not required for k.LAB to function. The k.LAB stack also includes a knowledge processor that can rewrite k.IM namespaces into OWL ontologies and perform consistency checks, alignments, inferences and other operations.

This repository is the official distribution of the ODO-IM ontology, whose latest master release is available as https://raw.githubusercontent.com/integratedmodelling/odo-im/master/releases/latest/odo.owl. Documentation will be available in this project in AsciiDoc source form and pre-built on the [k.LAB documentation site](https://docs.integratedmodelling.org).