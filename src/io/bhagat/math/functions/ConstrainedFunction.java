package io.bhagat.math.functions;

import io.bhagat.math.functions.exceptions.InputNotInDomainException;
import io.bhagat.math.functions.exceptions.OutputNotInCodomainException;
import io.bhagat.math.settheory.SetBase;

/**
 * A function that is constrained to a domain and codomain.
 * @param <E> The type of the domain.
 * @param <T> The type of the codomain.
 */
public abstract class ConstrainedFunction<E, T> implements Function<E, T> {

    private SetBase<E> domain;
    private SetBase<T> codomain;

    /**
     * Creates a new constrained function with the given domain and codomain.
     * @param domain the domain to set
     * @param codomain the codomain to set
     */
    public ConstrainedFunction(SetBase<E> domain, SetBase<T> codomain) {
        this.domain = domain;
        this.codomain = codomain;
    }

    /**
     * Constrains the input to the domain of the function and the output to the range of the function.
     * @param e the input
     * @return the output if it is in the range, otherwise throws an exception
     * @throws InputNotInDomainException
     * @throws OutputNotInCodomainException
     */
    public T constrainedF(E e) throws InputNotInDomainException, OutputNotInCodomainException {
        if (!domain.contains(e))
            throw new InputNotInDomainException(e.toString());
        T retVal = f(e);
        if (!codomain.contains(retVal))
            throw new OutputNotInCodomainException(retVal.toString());
        return retVal;
    }

    /**
     * @return the domain
     */
    public SetBase<E> getDomain() {
        return domain;
    }

    /**
     * @return the codomain
     */
    public SetBase<T> getCodomain() {
        return codomain;
    }
}
