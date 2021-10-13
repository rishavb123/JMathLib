package io.bhagat.math.functions;

import io.bhagat.math.functions.exceptions.InputNotInDomainException;
import io.bhagat.math.functions.exceptions.OutputNotInCodomainException;
import io.bhagat.math.settheory.SetBase;

public abstract class ConstrainedFunction<E, T> implements Function<E, T> {

    private SetBase<E> domain;
    private SetBase<T> codomain;

    public ConstrainedFunction(SetBase<E> domain, SetBase<T> codomain) {
        this.domain = domain;
        this.codomain = codomain;
    }

    public T constrainedF(E e) throws InputNotInDomainException, OutputNotInCodomainException {
        if (!domain.contains(e))
            throw new InputNotInDomainException(e.toString());
        T retVal = f(e);
        if (!codomain.contains(retVal))
            throw new OutputNotInCodomainException(retVal.toString());
        return retVal;
    }

    public SetBase<E> getDomain() {
        return domain;
    }

    public SetBase<T> getCodomain() {
        return codomain;
    }
}
