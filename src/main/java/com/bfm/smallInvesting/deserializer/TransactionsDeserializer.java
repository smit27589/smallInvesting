package com.bfm.smallInvesting.deserializer;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.bfm.smallInvesting.dto.Transaction;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class TransactionsDeserializer implements JsonDeserializer<Transaction> {

  @Override
  public Transaction deserialize(JsonElement json, Type typeOf,
      JsonDeserializationContext context) throws JsonParseException {
    try {

      final JsonObject jsonObject = json.getAsJsonObject();

      final Integer transactionId = jsonObject.get("viewKey").getAsJsonObject()
          .get("transactionId").getAsInt();
      
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      final Date postDate = sdf.parse(jsonObject.get("postDate").getAsString().substring(0, 10));
      final Double amount = jsonObject.get("amount").getAsJsonObject().get("amount").getAsDouble();
      final String currency = jsonObject.get("amount").getAsJsonObject().get("currencyCode").getAsString();
      final String description = jsonObject.get("description").getAsJsonObject().get("simpleDescription").getAsString();
      final String type = jsonObject.get("transactionBaseType").getAsString();
      
      final Transaction transaction = new Transaction();
      transaction.setTransactionId(transactionId);
      transaction.setAmount(amount);
      transaction.setCurrency(currency);
      transaction.setDescription(description.replaceAll("\\s+", " "));
      transaction.setPostDate(postDate);
      transaction.setProcessed(false);
      Double remainder = 0d;
      if (StringUtils.equalsIgnoreCase("credit", type)) {
        remainder = new BigDecimal(amount - Math.floor(amount)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
      } else {
        remainder = new BigDecimal(Math.ceil(amount) - amount).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
      }
      transaction.setRemainder(remainder);
      transaction.setType(type);
      return transaction;

    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

}
