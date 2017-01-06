协作上下文

The iddd_collaboration project uses Event Sourcing and
CQRS. It purposely avoids the use of an object-relational
mapper, showing that a simple JDBC-based query engine
and DTO matter can be used instead. This technique does
have its limitations, but it is meant to be small and fast
and require no configuration or annotations. It is not
meant to be perfect.

It may be helpful to make one additional mental note on
the iddd_collaboration CQRS implementation. To keep the
example simple it persists the Event Sourced write model
and the CQRS read model in one thread. Since two different
stores are used--LevelDB for the Event Journal and MySQL
for the read model--there may be very slight opportunities
for inconsistency, but not much. The idea was to keep the
two models as close to consistent as possible without
using the same data storage (and transaction) for both.
Two different storage mechanisms were used purposely to
demonstrate that they can be separate.