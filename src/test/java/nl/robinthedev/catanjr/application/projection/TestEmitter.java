package nl.robinthedev.catanjr.application.projection;

import java.util.function.Predicate;
import javax.annotation.Nonnull;
import org.axonframework.common.Registration;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.queryhandling.SubscriptionQueryBackpressure;
import org.axonframework.queryhandling.SubscriptionQueryMessage;
import org.axonframework.queryhandling.SubscriptionQueryUpdateMessage;
import org.axonframework.queryhandling.UpdateHandlerRegistration;

class TestEmitter implements QueryUpdateEmitter {
  @Override
  public <U> void emit(
      @Nonnull Predicate<SubscriptionQueryMessage<?, ?, U>> filter,
      @Nonnull SubscriptionQueryUpdateMessage<U> update) {}

  @Override
  public void complete(@Nonnull Predicate<SubscriptionQueryMessage<?, ?, ?>> filter) {}

  @Override
  public void completeExceptionally(
      @Nonnull Predicate<SubscriptionQueryMessage<?, ?, ?>> filter, @Nonnull Throwable cause) {}

  @Override
  public boolean queryUpdateHandlerRegistered(@Nonnull SubscriptionQueryMessage<?, ?, ?> query) {
    return false;
  }

  @Override
  public <U> UpdateHandlerRegistration<U> registerUpdateHandler(
      SubscriptionQueryMessage<?, ?, ?> query,
      SubscriptionQueryBackpressure backpressure,
      int updateBufferSize) {
    return null;
  }

  @Override
  public <U> UpdateHandlerRegistration<U> registerUpdateHandler(
      @Nonnull SubscriptionQueryMessage<?, ?, ?> query, int updateBufferSize) {
    return null;
  }

  @Override
  public Registration registerDispatchInterceptor(
      @Nonnull
          MessageDispatchInterceptor<? super SubscriptionQueryUpdateMessage<?>>
              dispatchInterceptor) {
    return null;
  }
}
